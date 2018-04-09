package application.timers;

import java.util.Timer;
import java.util.TimerTask;

import application.Constants;
import application.net.SwarmRequest;
import application.net.swarm.ReviewItem;

public class SwarmUpdateScheduler {
	
	
	private static SwarmUpdateScheduler swarmScheduler = null;
	private Timer timer = new Timer();
	
	private SwarmUpdateScheduler() {
		// TODO Auto-generated constructor stub
	}
	
	public static SwarmUpdateScheduler getInstance() {
		if(swarmScheduler == null) {
			swarmScheduler = new SwarmUpdateScheduler();
		}
		return swarmScheduler;
	}
	
	private class PeriodicSwarmUIUpdate extends TimerTask{
		String url = null;
		
		@Override
		public void run() {
			String url = Constants.SwarmReviewUrl;
			SwarmRequest<ReviewItem> swarmReq = new SwarmRequest<ReviewItem>(url);
			
			/**
			 * TODO: Implement listeners for handling new list of reviews 
			 */
			String ret;
			
			/*
			 * Updated UI list each two minutes
			 */
			timer.schedule(new PeriodicSwarmUIUpdate(), 60 * 2 * 1000);
			
		}
		
	}

}
