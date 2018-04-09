package application.net.swarm;

import java.util.List;

public class ReviewItem extends SwarmItem {
	String author;
	String url;
	List<String> reviewers;
	List<String> voteUps;
	boolean hasVoteUp;
	int swarmCount;
	static int count;
	
	public ReviewItem(String url) {
		//TODO url should be validated
		author = "Piotr";
		hasVoteUp = false;
		this.url = url;
		count ++;
		this.swarmCount = count;
	}
	
	@Override
	public String toString() {
		return this.url;
	}

}
