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
		 * z jsona wypierdalamy zb�dn� znaki nakzuj�ce parsowa� to niczym obiekt a nie tablic� tj z pocz�tku to:
		 * {"data": 
		 * a na ko�cu to:
		 * }
		 * 
		 * Mo�na ewentualnie pogada� z Tobiaszem, �eby zwracali czyst� tablic� a nie obiekt,
		 * ale to ch�opu szkoda dupy zawraca� bo i tak jest zabiegany.
		 */
		sb.replace(0, 9, "");
		sb.replace(sb.length()-1, sb.length(),"");
		reader.close();
		System.out.println(sb);
		return sb.toString();

	}
}
