
public class GraphAlgorithmAttributes {
    private Color color;
    private int discoveryTime;
    private int finishTime;
    private int distance;
    private UserProfile parent;

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }
    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public int getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public UserProfile getParent() {
        return parent;
    }
    public void setParent(UserProfile parent) {
        this.parent = parent;
    }
}
