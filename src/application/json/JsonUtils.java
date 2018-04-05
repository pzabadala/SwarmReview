package application.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
	Gson gson;
	
	public static Map<String, List<String>> getUsersFromJson(String json){
		Gson gson = new Gson();
		User[] users = gson.fromJson(json, User[].class);
		
		/*
		Type collectionType = new TypeToken<List<User>>(){}.getType();
		List<User> userList = (List<User>) new Gson()
		               .fromJson( json , collectionType);
		*/
		
		List<User> userList = Arrays.asList(users);
		List<String> nameList = new ArrayList<>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (User user : userList) {
			System.out.println(user.mysingle_id);
			if(map.containsKey(user.team__name)) {
				map.get(user.team__name).add(user.mysingle_id);
			}else {
				map.put(user.team__name, new ArrayList<String>());
			}
		}
		return map;
	}
}