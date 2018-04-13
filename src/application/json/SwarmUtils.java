package application.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;



public class SwarmUtils {
	
	public static List<ReviewJsonItem> getReviewsFromJson(String json){
		
		Gson gson = new Gson();
		JesonReviweResponse jsonResponse = gson.fromJson(json, JesonReviweResponse.class);
		ReviewJsonItem [] reviews = jsonResponse.reviews;
		List<ReviewJsonItem> reviewList = Arrays.asList(reviews);
		
		return reviewList;
	}
	
	/*
	 * Swarmowy Json jest jakiœ takiœ dziwny 
	 * i nie potrawiê wyci¹gnaæ nazwy usera, 
	 * który zrobi³ voteupa ;/
	 * 
	 */
	public static class ReviewJsonItem{
		/*
		 * String fields= "state,id,author,created,description, participants";
		 */
		Participants participants;
		public String state;
		public String id;
		public String author;
		public String created;
		public String description;	
	}
	
	public class Participants{
		Vote vote;
	}
	public class Vote{
		public int value;
		public int version;
		public boolean isStale;
	}
	
	public class JesonReviweResponse{
		int lastSeen;
		ReviewJsonItem [] reviews;
		int totalCount;
	}
}
