import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
        GraphGenerator graphGenerator = new GraphGenerator();
        SocialNetwork socialNetwork = graphGenerator.generate("C:\\Users\\Diogenes\\eclipse-workspace\\GraphProject/input.txt");
        
		socialNetwork.printGraph();
		System.out.println();

		socialNetwork.recommenderFriendships();
	}

}