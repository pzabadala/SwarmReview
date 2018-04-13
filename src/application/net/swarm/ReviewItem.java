package application.net.swarm;



public class ReviewItem extends SwarmItem {
	
	public ReviewItem(String teamMembers) {
		participants = teamMembers;
	}
	public static String participants;
	public static String state = "state=needsReview";
	public static String fields= "fields=state,id,author,created,participants";

}
