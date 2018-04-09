package application.net.swarm;

import java.lang.reflect.Field;

import application.Constants;

public class SwarmItem {
	transient String baseUrl = Constants.SwarmReviewUrl;
	
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

