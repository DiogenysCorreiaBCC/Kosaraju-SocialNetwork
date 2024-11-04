import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphGenerator {
	
    public SocialNetwork generate(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scan = new Scanner(file);

        SocialNetwork socialNetwork = new SocialNetwork();
        
        int v = scan.nextInt();
        int e = scan.nextInt();
        scan.nextLine();

        for (int i = 0; i < v; i++) {
            String vertexLabel = scan.nextLine().trim();
            UserProfile userProfile = new UserProfile(vertexLabel);
            socialNetwork.addUser(userProfile);
        }

        for (int i = 0; i < e; i++) {
            String edge = scan.nextLine().trim();
            String vertex1 = edge.substring(0, 1);
            String vertex2 = edge.substring(1, 2);

            UserProfile follower = socialNetwork.findUserByName(vertex1);
            UserProfile followed = socialNetwork.findUserByName(vertex2);

            if (follower != null && followed != null) {
                Connection connection = new Connection(follower, followed);
                socialNetwork.addConnection(connection);
            }
        }

        scan.close();
        return socialNetwork;
    }
}
