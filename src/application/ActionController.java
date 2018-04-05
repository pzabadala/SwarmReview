package application;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import application.json.JsonUtils;
import net.ImsPortalRequest;
import net.SwarmRequest;

public class ActionController {
	public static boolean login(String login, String pass) {
		SwarmRequest sq = new SwarmRequest(login, pass);
		try {
			sq.getRESTResponse();
			return true;
		}catch (IOException ex) {
			return false;
		}
		
	}
	
	public static List<String> getTeamMates(String team){
		return null;
	}
	
	public static List<String> getImsMembers(){
		try {
			ImsPortalRequest imsMembersReq = new ImsPortalRequest(Constans.ImsPortalMembersUrl);
			String membersJson = imsMembersReq.getHttpResponse();
			JsonUtils.getUsersFromJson(membersJson);
		}catch (IOException ex) {
			return null;
		}
		return null;
	}
}
