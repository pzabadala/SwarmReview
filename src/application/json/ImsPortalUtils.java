package application.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class ImsPortalUtils {
	Gson gson;
	
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
	
	private class ImsPortalUser {
		String name;
		boolean active;
		String team__name;
		String mysingle_id;

	}
}