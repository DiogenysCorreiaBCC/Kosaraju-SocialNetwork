import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserProfile implements Comparable<UserProfile> {
    private String userName;
    private List<UserProfile> following;
    private List<UserProfile> recommendations;
    private GraphAlgorithmAttributes graphAttributes;

    @Override
    public int compareTo(UserProfile o) {
        return Integer.compare(o.getFinishTime(), this.getFinishTime());
    }
    
    public void addRecomendation(UserProfile recommendedUser) {
    	this.recommendations.add(recommendedUser);
    }

    public UserProfile(String userName) {
        this.userName = userName;
        this.following = new LinkedList<>();
        this.graphAttributes = new GraphAlgorithmAttributes();
        this.recommendations = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public List<UserProfile> getFollowing() {
        return following;
    }

    public void addFollowing(UserProfile vertex) {
        following.add(vertex);
    }

    public Color getColor() {
        return graphAttributes.getColor();
    }

    public void setColor(Color color) {
        graphAttributes.setColor(color);
    }

    public int getDiscoveryTime() {
        return graphAttributes.getDiscoveryTime();
    }

    public void setDiscoveryTime(int discoveryTime) {
        graphAttributes.setDiscoveryTime(discoveryTime);
    }

    public int getFinishTime() {
        return graphAttributes.getFinishTime();
    }

    public void setFinishTime(int finishTime) {
        graphAttributes.setFinishTime(finishTime);
    }

    public int getDistance() {
        return graphAttributes.getDistance();
    }

    public void setDistance(int distance) {
        graphAttributes.setDistance(distance);
    }

    public UserProfile getParent() {
        return graphAttributes.getParent();
    }

    public void setParent(UserProfile parent) {
        graphAttributes.setParent(parent);
    }

    public void setName(String name) {
        this.userName = name;
    }

    public void setFollowing(List<UserProfile> following) {
        this.following = following;
    }

    public GraphAlgorithmAttributes getGraphAttributes() {
        return graphAttributes;
    }

	public List<UserProfile> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<UserProfile> recommendations) {
		this.recommendations = recommendations;
	}
}
