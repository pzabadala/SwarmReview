package application.net.swarm;

import java.util.List;

public class ReviewItem extends SwarmItem {
	
	public ReviewItem() {

	}
	String participants;
	String state = "needsReview";
	String fields= "state,id,author,created,description";

}
