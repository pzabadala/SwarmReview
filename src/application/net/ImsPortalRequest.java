package application.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class ImsPortalRequest {
	
	public ImsPortalRequest(String url) {
		imsPortalMemberUrl = url;
	}
	
	public String getHttpResponse() throws IOException{
		String jsonResponse = getDataFromServer();
		return jsonResponse;
		
	}
	
	String imsPortalMemberUrl;

	public String getDataFromServer() throws IOException  {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(imsPortalMemberUrl);
		URLConnection urlConnection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		/**
		 * z jsona wypierdalamy zbêdnê znaki nakzuj¹ce parsowaæ to niczym obiekt a nie tablicê tj z pocz¹tku to:
		 * {"data": 
		 * a na koñcu to:
		 * }
		 * 
		 * Mo¿na ewentualnie pogadaæ z Tobiaszem, ¿eby zwracali czyst¹ tablicê a nie obiekt,
		 * ale to ch³opu szkoda dupy zawracaæ bo i tak jest zabiegany.
		 */
		sb.replace(0, 9, "");
		sb.replace(sb.length()-1, sb.length(),"");
		reader.close();
		System.out.println(sb);
		return sb.toString();

	}
}
