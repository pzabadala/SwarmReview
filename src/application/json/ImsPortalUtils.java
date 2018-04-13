package application.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class ImsPortalUtils {
	Gson gson;
	
	/*
	 *  sample data:
	 *  {"name": "Piotr Zabadala", "team__name": "IMS/VoLTE - ePDG", "mysingle_id": "p.zabadala", "active": true}
	 */
	
	public enum ImsTeam{
		/**
		 * TODO check and add all teams from IMS portal
		 */
		EPDG("IMS/VoLTE - ePDG"),
		GUM_IVOLTE("Gumi-VoLTE");
		String jsonteam;
		ImsTeam(String team) {
			jsonteam = team;
		}
		public String getJsonTeam() {
			return jsonteam;
		}
		//"IMS/VoLTE - ePDG"
		

		
	}
	public static Map<String, List<String>> getUsersFromJson(String json){
		Gson gson = new Gson();
		ImsPortalUser[] users = gson.fromJson(json, ImsPortalUser[].class);
		
		List<ImsPortalUser> userList = Arrays.asList(users);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (ImsPortalUser user : userList) {
			if(map.containsKey(user.team__name)) {
				map.get(user.team__name).add(user.mysingle_id);
			}else {
				map.put(user.team__name, new ArrayList<String>());
			}
		}
		return map;
	}
	
	public static String getUserIdStringFromMap(ImsTeam team, Map<String, List<String>> map) {
		if(map == null)
			return null;
		StringBuilder sb = new StringBuilder();
		List<String> list = map.get(team.getJsonTeam());
		for(String member: list) {
			sb.append("participants=");
			sb.append(member);
			sb.append("&");
		}
		
		return sb.toString();
	}
	
	private class ImsPortalUser {
		String name;
		boolean active;
		String team__name;
		String mysingle_id;

	}
}