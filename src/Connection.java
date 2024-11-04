
public class Connection {
    private UserProfile follower;
    private UserProfile followed;

    public Connection(UserProfile follower, UserProfile followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public UserProfile getFollower() {
        return follower;
    }

    public UserProfile getFollowed() {
        return followed;
    }
}
