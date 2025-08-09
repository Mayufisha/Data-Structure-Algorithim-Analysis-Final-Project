import java.util.*;

public class SocialNetwork {
    private final Map<String, Map<String, Integer>> adj = new HashMap<>();
    private Map<String, User> users;
    private NetworkStats stats;

    private void ensureAdj(String u) {
        adj.computeIfAbsent(u, k -> new HashMap<>());
    }

    public SocialNetwork() {
        this.users = new HashMap<>();
        this.stats = new NetworkStats(users);
    }

    public boolean addUser(String username) {
        if (!User.isValidUsername(username)) return false;
        username = User.sanitizeUsername(username);
        if (users.containsKey(username)) return false;
        try {
            users.put(username, new User(username));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean userExists(String username) {
        return users.containsKey(User.sanitizeUsername(username));
    }

    // weighted friendship
    public boolean addFriendship(String user1, String user2, int weight) {
        if (!User.areValidUsernames(user1, user2)) return false;
        user1 = User.sanitizeUsername(user1);
        user2 = User.sanitizeUsername(user2);
        if (user1.equals(user2)) return false;
        if (!users.containsKey(user1) || !users.containsKey(user2)) return false;
        if (users.get(user1).isFriendsWith(user2)) return false;
        if (weight <= 0) return false;

        // existing unweighted storage to keep the current features working
        users.get(user1).addFriend(user2);
        users.get(user2).addFriend(user1);

        // weighted storage
        ensureAdj(user1); ensureAdj(user2);
        adj.get(user1).put(user2, weight);
        adj.get(user2).put(user1, weight);
        return true;
    }

    // Back-compat: if other code calls old method, default weight = 1 (to avoid bugs)
    public boolean addFriendship(String user1, String user2) {
        return addFriendship(user1, user2, 1);
    }

    // Expose adjacency for algorithms
    Map<String, Map<String, Integer>> getAdjacency() { return adj; }

    public Set<String> getUserFriends(String username) {
        if (!User.isValidUsername(username)) return new HashSet<>();
        username = User.sanitizeUsername(username);
        if (!users.containsKey(username)) return new HashSet<>();
        return users.get(username).getFriends();
    }

    public boolean areDirectlyConnected(String user1, String user2) {
        return users.get(User.sanitizeUsername(user1)).isFriendsWith(User.sanitizeUsername(user2));
    }

    public boolean areConnected(String user1, String user2) {
        return GraphOperations.areConnected(user1, user2, users);
    }

    public Set<String> findMutualFriends(String user1, String user2) {
        return GraphOperations.findMutualFriends(user1, user2, users);
    }

    public Set<String> suggestFriends(String username) {
        return GraphOperations.suggestFriends(username, users);
    }

    public List<String> findShortestPath(String user1, String user2) {
        return GraphOperations.findShortestPath(user1, user2, users);
    }

    public Set<String> getAllUsers() {
        return new HashSet<>(users.keySet());
    }

    public NetworkStats getStats() {
        return stats;
    }


    // Update initializeSampleData() to include weights
    public void initializeSampleData() {
        String[] sampleUsers = {"alice","bob","charlie","diana","eve","frank",
                "grace","heidi","ivan","judy"};

        Object[][] friendships = {
                {"alice","bob",2}, {"alice","charlie",5}, {"bob","diana",1},
                {"charlie","eve",2}, {"diana","frank",3}, {"eve","frank",4},
                {"grace","heidi",1}, {"heidi","ivan",2}, {"ivan","judy",2},
                {"judy","grace",3}, {"bob","grace",5}, {"alice","heidi",4}
        };
        for (String u : sampleUsers) addUser(u);
        for (Object[] f : friendships) addFriendship((String)f[0], (String)f[1], (Integer)f[2]);
    }
}
