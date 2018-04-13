package application.timers;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import application.ActionController;
import application.Constants;
import application.json.ImsPortalUtils;
import application.json.SwarmUtils;
import application.json.SwarmUtils.ReviewJsonItem;
import application.net.ImsPortalRequest;
import application.net.SwarmRequest;
import application.net.swarm.ReviewItem;

public class SwarmUpdateScheduler {
	
	
	private static SwarmUpdateScheduler swarmScheduler = null;
	ImsPortalUtils.ImsTeam team = ImsPortalUtils.ImsTeam.EPDG;
	
	private Timer timer = new Timer();
	
	private SwarmUpdateScheduler() {
	}
	
	public static SwarmUpdateScheduler getInstance() {
		if(swarmScheduler == null) {
			swarmScheduler = new SwarmUpdateScheduler();
		}
		return swarmScheduler;
	}
	
	public void startSwarmListUpdate() {
		/*
		 * startowe opoznienie 0.5 sekundy, ¿eby UI tak nie przywisa³
		 */
        timer.schedule(new PeriodicSwarmUIUpdate(), 500);
    }
	
	public void stopSwarmListUpdate() {
		/*
		 * koniec !!!
		 */
        timer.cancel();
    }
	
	
	
	private class PeriodicSwarmUIUpdate extends TimerTask{
		String teamMembers = ImsPortalUtils.getUserIdStringFromMap(team, ActionController.members);
		ReviewItem reviewItem = new ReviewItem(teamMembers);
		@Override
		public void run(){	
			/*
			 * I tutaj zalczy³em faila bo napisuj¹c run 
			 * nie mogê rzucaæ wyj¹tkami i muszê je handlowaæ w ciele metody 
			 * (smuteczek) ;/ 
			 */
			SwarmRequest swarmReq = new SwarmRequest(reviewItem);
			try {
				String json = swarmReq.getDataFromServer();
				List<ReviewJsonItem> ReviewJsonItem = SwarmUtils.getReviewsFromJson(json);
				for(ReviewJsonItem item: ReviewJsonItem) {
					System.out.println(item.id);
				}
				
				
			} catch (IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * TODO: Implement listeners for handling new list of reviews 
			 */
			/*
			 * Updated UI list each two minutes
			 */
			 finally {
				 timer.schedule(new PeriodicSwarmUIUpdate(), 60 * 2 * 1000);
			 }
			
		}
		
	}

}
