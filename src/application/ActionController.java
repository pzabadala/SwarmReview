package application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import application.net.ImsPortalRequest;
import application.json.ImsPortalUtils;
import application.net.SwarmRequest;
import application.net.swarm.LoginItem;
import application.timers.SwarmUpdateScheduler;

public class ActionController {
	
	public static  Map<String, List<String>> members;
	
	public static boolean login(String login, String pass) {
		try {
			SwarmRequest sq = new SwarmRequest (login, pass, new LoginItem());
			String loginResponse = sq.getRESTResponse();
			/*
			 * set static cache that keeps user and pass
			 */
			sq.setCache(login, pass);
			return true;
		}catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}catch (IllegalAccessException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static void startUpdateReviewList() {
		getImsMembers();
		SwarmUpdateScheduler scheduler = SwarmUpdateScheduler.getInstance();
		scheduler.startSwarmListUpdate();
		
	}
	
	public static void stopUpdateReviewList() {
		SwarmUpdateScheduler scheduler = SwarmUpdateScheduler.getInstance();
		scheduler.stopSwarmListUpdate();
	}
	
	public static List<String> getImsMembers(String team){
		return members.get(team);
	}
	
	public static List<String> getImsMembers(){
		try {
			ImsPortalRequest imsMembersReq = new ImsPortalRequest(Constants.ImsPortalMembersUrl);
			String membersJson = imsMembersReq.getHttpResponse();
			members = ImsPortalUtils.getUsersFromJson(membersJson);
		}catch (IOException ex) {
			return null;
		}
		return null;
	}
}
