package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class SwarmRequest {

	private String baseUrl;
	private String username;
	private String password;

	public SwarmRequest(String username, String password) {
		this.baseUrl = "https://review1716.sec.samsung.net/api/v2/reviews?author[]=" + username + "max=1";
		this.username = username;
		this.password = password;
	}
	
	public SwarmRequest(String username, String password, String url) {
		this.baseUrl = url;
		this.username = username;
		this.password = password;
	}

	public String getRESTResponse() throws IOException{
		return getDataFromServer();
	}

	public String getDataFromServer() throws IOException  {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(baseUrl);
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

}
