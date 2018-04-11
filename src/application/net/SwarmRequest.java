package application.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.reflections.ReflectionUtils;

import application.net.swarm.LoginItem;
import application.net.swarm.ReviewItem;
import application.net.swarm.SwarmItem;

public class SwarmRequest {

	private String baseUrl;
	private String username;
	private String password;
	SwarmItem item;

	private static UserCache userCache = null;
	

	public SwarmRequest(String username, String password, SwarmItem item) {
		baseUrl = item.baseUrl;
		this.item = item;
		
		if(userCache == null) {
			this.username = username;
			this.password = password;
		}else {
			this.username = SwarmRequest.userCache.user;
			this.password = SwarmRequest.userCache.password;
		}
	}
	
	public SwarmRequest(SwarmItem item) {
		baseUrl = item.baseUrl;
		this.item = item;

		if(userCache != null) {
			this.username = SwarmRequest.userCache.user;
			this.password = SwarmRequest.userCache.password;
		}else {
			new Exception("Login Failed");
		}
	}

	public String getRESTResponse() throws IOException, IllegalAccessException{
		return getDataFromServer();
	}

	public String getDataFromServer() throws IOException, IllegalAccessException  {
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		
		Map<String,String> httpParams = new HashMap<String, String>();
		httpParams = getFieldsMap();
		
		for(String key:httpParams.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(httpParams.get(key));
			sb.append("&");
		}
		
		URL url = new URL(baseUrl +sb.toString());
		HttpURLConnection urlConnection = setUsernamePassword(url);
		urlConnection.setRequestMethod("GET");
		
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		
		return sb.toString();
	}

	private HttpURLConnection setUsernamePassword(URL url) throws IOException {
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		String authString = username + ":" + password;
		String authStringEnc = new String(Base64.getEncoder().encodeToString(authString.getBytes()));
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return urlConnection;
	}
	
	public Map<String, String> getFieldsMap() throws IllegalAccessException {

		Field[] fields = item.getClass().getDeclaredFields();
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0; i < fields.length; i++) {
			map.put(fields[i].getName(), (String)fields[i].get(null));
		}
		return map;
	}
	
	public void setCache(String user, String pass) {
		userCache = new UserCache(user, pass);
	}
	
	

	
	/*
	 * Runtime static cache created after successful login to swarm over http
	 */
	private static class UserCache{
		UserCache(String user, String pass) {
			this.user = user;
			this.password = pass;
		}
		String user;
		String password;
	}

}
