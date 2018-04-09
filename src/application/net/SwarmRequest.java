package application.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import application.net.swarm.ReviewItem;
import application.net.swarm.SwarmItem;

public class SwarmRequest<T extends SwarmItem> {

	private String baseUrl;
	private String username;
	private String password;
	T t;

	private static UserCache userCache;

	public SwarmRequest(String username, String password) {
		this.baseUrl = "https://review1716.sec.samsung.net/api/v2/reviews?max=1";
		this.username = username;
		this.password = password;
	}
	
	public SwarmRequest(String username, String password, String url) {
		this.baseUrl = url;
		this.username = username;
		this.password = password;
	}
	
	public SwarmRequest(String url) {
		this.baseUrl = url;
		this.username = SwarmRequest.userCache.user;
		this.password = SwarmRequest.userCache.password;;
	}

	public String getRESTResponse() throws IOException, IllegalAccessException{
		return getDataFromServer();
	}

	public String getDataFromServer() throws IOException, IllegalAccessException  {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(baseUrl + t.getQuerryUrl());
		URLConnection urlConnection = setUsernamePassword(url);
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}

	private URLConnection setUsernamePassword(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		String authString = username + ":" + password;
		String authStringEnc = new String(Base64.getEncoder().encodeToString(authString.getBytes()));
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return urlConnection;
	}
	
	public void setCache(String user, String pass) {
		userCache = new UserCache(user, pass);
	}
	
	/*
	 * Runtime static cache created after successful login to swarm
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
