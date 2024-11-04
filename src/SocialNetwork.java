import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocialNetwork {
    private List<UserProfile> userProfiles;
    private List<Connection> connections;
    private int timer;
    
    /**
     * Gera e imprime recomendações de amizade para os usuários da rede social com base
     * nas componentes fortemente conectadas (SCCs).
     *
     * Este método identifica as SCCs utilizando o algoritmo de Kosaraju, imprime cada componente
     * e gera recomendações de amizade para os usuários que pertencem ao mesmo componente, mas
     * ainda não se seguem.
     */
    public void recommenderFriendships() {
    	List<List<UserProfile>> stronglyConnectedComponents = this.stronglyConnectedComponents();
    	this.printStronglyConnectedComponents(stronglyConnectedComponents);
    	
    	for(List<UserProfile> component: stronglyConnectedComponents) {
    		processComponentRecommendations(component);
    	}
    }
    
    /**
     * Processa as recomendações de amizade para todos os usuários em um componente.
     *
     * @param component A lista de usuários que formam um componente fortemente conectado.
     */
    private void processComponentRecommendations(List<UserProfile> component) {
    	for(UserProfile user: component) {
    		user.getRecommendations().clear();
    		generateRecommendationsForUser(user, component);
    	}
    }
    
    /**
     * Gera recomendações de amizade para um usuário específico dentro de um componente.
     *
     * @param user O usuário para o qual as recomendações estão sendo geradas.
     * @param component A lista de usuários que formam um componente fortemente conectado.
     */
    private void generateRecommendationsForUser(UserProfile user, List<UserProfile> component) {
    	for(UserProfile otherUser: component) {
    		if(!otherUser.getFollowing().contains(user) && !user.equals(otherUser)) {
    			user.addRecomendation(otherUser);
    		}
    	}
    	System.out.println();
    	printRecommendationsForUser(user);
    }
    
    /**
     * Imprime as recomendações de amizade para um usuário, caso existam.
     *
     * @param user O usuário cujas recomendações serão impressas.
     */
    private void printRecommendationsForUser(UserProfile user) {
        System.out.println();
        
    	if(!user.getRecommendations().isEmpty()) {
            System.out.println("Recomendações para " + user.getUserName() + ":");
            for (UserProfile recommendedUser : user.getRecommendations()) {
                System.out.println(recommendedUser.getUserName());
            }
    	}
    }
    
    /**
     * Identifica e retorna os componentes fortemente conectados da rede social.
     *
     * Este método utiliza o algoritmo de busca em profundidade (DFS) para explorar a rede social
     * e, em seguida, transpõe o grafo. Após a transposição, ele ordena os perfis de usuário com
     * base no tempo de finalização da DFS. Em seguida, realiza outra busca em profundidade no
     * grafo transposto para encontrar e armazenar todos os componentes fortemente conectados.
     *
     * @return Uma lista de listas, onde cada sublista contém perfis de usuários que formam
     *         um componente fortemente conectado. Se não houver componentes, retorna uma lista vazia.
     */
    private List<List<UserProfile>> stronglyConnectedComponents(){
    	this.DFS();	
    	SocialNetwork transposed = this.transpose(); 
    	
    	List<UserProfile> orderedProfiles = getOrderedProfiles();
    	
    	initializeTransposedGraph(transposed);
    	
    	List<List<UserProfile>> stronglyConnectedComponents = new ArrayList<>();
    	
    	findStronglyConnectedComponents(transposed, orderedProfiles, stronglyConnectedComponents);
    	
    	return stronglyConnectedComponents;
    }
    
    /**
     * Obtém uma lista de perfis de usuários ordenada por tempo de finalização.
     *
     * @return Lista de usuários ordenada.
     */
    private List<UserProfile> getOrderedProfiles(){
    	List<UserProfile> orderedProfiles = new ArrayList<>(this.userProfiles);
    	Collections.sort(orderedProfiles);
    	
    	return orderedProfiles;
    }
    
    /**
     * Inicializa o grafo transposto e redefine o temporizador para a DFS.
     *
     * @param transposed O grafo transposto a ser inicializado.
     */
    private void initializeTransposedGraph(SocialNetwork transposed) {
    	transposed.initializer();
    	transposed.setTimer(0);
    }
    
    /**
     * Encontra e armazena as componentes fortemente conectadas.
     *
     * @param transposed O grafo transposto.
     * @param orderedProfiles Lista de perfis de usuários ordenados.
     * @param stronglyConnectedComponents Lista onde os componentes encontrados serão armazenados.
     */
    private void findStronglyConnectedComponents(SocialNetwork transposed, List<UserProfile> orderedProfiles, List<List<UserProfile>> stronglyConnectedComponents) {
    	for(UserProfile user: orderedProfiles) {
    		UserProfile transposedUser = transposed.findUserByName(user.getUserName());
    		
    		if(transposedUser.getColor().equals(Color.WHITE)) {
    			List<UserProfile> currentComponent = new ArrayList<>();
    			
    			transposed.DFS_Visit(transposedUser, currentComponent);
    			
    			stronglyConnectedComponents.add(currentComponent);
    		}
    	}
    }
    
    private void DFS() {
    	this.initializer();
    	this.setTimer(0);
    	
    	for(UserProfile user: this.userProfiles) {
    		if(user.getColor().equals(Color.WHITE)) {
    			DFS_Visit(user);
    		}
    	}
    }
    
    private void DFS_Visit(UserProfile user) {
    	user.setDiscoveryTime(++this.timer);
    	user.setColor(Color.GRAY);
    	
    	for(UserProfile v: user.getFollowing()) {
    		if(v.getColor().equals(Color.WHITE)) {
    			v.setParent(user);
    			DFS_Visit(v);
    		}
    	}
    	
    	user.setColor(Color.BLACK);
    	user.setFinishTime(++this.timer);
    }
    
    private void DFS_Visit(UserProfile user, List<UserProfile> currentComponent) {
    	user.setDiscoveryTime(++this.timer);
    	user.setColor(Color.GRAY);
    	
    	currentComponent.add(user);
    	
    	for(UserProfile v: user.getFollowing()) {
    		if(v.getColor().equals(Color.WHITE)) {
    			v.setParent(user);
    			DFS_Visit(v, currentComponent);
    		}
    	}
    	
    	user.setColor(Color.BLACK);
    	user.setFinishTime(++this.timer);
    }
    
    private SocialNetwork transpose() {
    	SocialNetwork transposedNetwork = new SocialNetwork();
    	
    	for(UserProfile user: this.userProfiles) {
    		UserProfile transposedUser = new UserProfile(user.getUserName());
    		transposedNetwork.addUser(transposedUser);
    	}
    	
    	for(Connection connection: this.connections) {
    		UserProfile originalFollower = connection.getFollower();
    		UserProfile originalFollowed = connection.getFollowed();
    		
    		UserProfile transposedFollower = transposedNetwork.findUserByName(originalFollower.getUserName());
    		UserProfile transposedFollowed = transposedNetwork.findUserByName(originalFollowed.getUserName());
    		
    		transposedNetwork.addConnection(new Connection(transposedFollowed, transposedFollower));
    	}
    	//transposedNetwork.printGraph();
    	return transposedNetwork;
    }
    
    public void printStronglyConnectedComponents(List<List<UserProfile>> components) {
    	System.out.println("Componentes Fortemente Conexas:");
        for (List<UserProfile> component : components) {
        	System.out.println();
            for(UserProfile u: component) {
            	System.out.printf("%s", u.getUserName());
            }
        }
    }

    public void printGraph() {
        for (UserProfile user : this.userProfiles) {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUserName()).append(": ");
            
            List<UserProfile> following = user.getFollowing();
            
            if (following.isEmpty()) {
                sb.append("");
            } else {
                for (int i = 0; i < following.size(); i++) {
                	sb.append(following.get(i).getUserName());
                    if (i < following.size() - 1) {
                        sb.append(", ");
                    }else {
                    	sb.append(".");
                    }
                }
            }
            
            System.out.println(sb.toString());
        }
    }


    private void initializer() {
    	for(UserProfile v: this.userProfiles) {
    		v.setColor(Color.WHITE);
    		v.setParent(null);
    		v.setDistance(-1);
    	}
    }
    
    /**
     * Adiciona uma nova conexão entre dois usuários.
     *
     * Este método registra uma conexão na lista de conexões e atualiza
     * a relação de seguimento entre os usuários envolvidos. O seguidor
     * (follower) é adicionado à lista de seguindo (following) do usuário
     * que está sendo seguido (followed).
     *
     * @param connection A conexão a ser adicionada, que contém informações 
     *                   sobre o seguidor e o usuário que está sendo seguido.
     */
    public void addConnection(Connection connection) {
    	connections.add(connection);
        connection.getFollower().addFollowing(connection.getFollowed());
    }
    
    /**
     * Encontra um UserProfile pelo nome de usuário especificado.
     *
     * Este método itera pela lista de perfis de usuário para localizar
     * um usuário cujo nome de usuário corresponda ao nome fornecido. 
     * Se uma correspondência for encontrada, o objeto UserProfile correspondente 
     * é retornado; caso contrário, retorna null.
     *
     * @param UserName O nome de usuário do usuário a ser pesquisado.
     * @return O objeto UserProfile correspondente ao nome de usuário especificado,
     *         ou null se nenhum usuário com esse nome existir.
     */
    public UserProfile findUserByName(String UserName) {
        for (UserProfile user: userProfiles) {
            if (user.getUserName().equals(UserName)) {
                return user;
            }
        }
        
        return null;
    }
    
    public SocialNetwork() {
        this.userProfiles = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public List<UserProfile> getUser() {
        return userProfiles;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void addUser(UserProfile user) {
    	userProfiles.add(user);
    }

	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
}
