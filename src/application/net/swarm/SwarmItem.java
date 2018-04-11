package application.net.swarm;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import application.Constants;

public class SwarmItem {
	
	public static String baseUrl = Constants.SwarmReviewUrl;
	public String getFieldsList() throws IllegalAccessException {
			StringBuilder sb = new StringBuilder();
			sb.append("fields=");
			Field[] fields = this.getClass().getDeclaredFields();
			for(int i =0; i < fields.length; i++) {
				sb.append(fields[i].getName());
				sb.append(",");
			}
		return sb.toString();
	}
	

	public String getQuerryUrl() {
		
		return "";
	}
}

