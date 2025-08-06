import java.util.*;

public class SocialNetwork {
    private Map<String, User> users;
    private NetworkStats stats;

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

    public boolean addFriendship(String user1, String user2) {
        if (!User.areValidUsernames(user1, user2)) return false;
        user1 = User.sanitizeUsername(user1);
        user2 = User.sanitizeUsername(user2);
        if (user1.equals(user2)) return false;
        if (!users.containsKey(user1) || !users.containsKey(user2)) return false;
        if (users.get(user1).isFriendsWith(user2)) return false;

        users.get(user1).addFriend(user2);
        users.get(user2).addFriend(user1);
        return true;
    }

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

    public void initializeSampleData() {
        String[] sampleUsers = {
                "alice", "bob", "charlie", "diana", "eve", "frank",
                "grace", "heidi", "ivan", "judy"
        };

        String[][] friendships = {
                {"alice", "bob"}, {"alice", "charlie"}, {"bob", "diana"},
                {"charlie", "eve"}, {"diana", "frank"}, {"eve", "frank"},
                {"grace", "heidi"}, {"heidi", "ivan"}, {"ivan", "judy"},
                {"judy", "grace"}, {"bob", "grace"}, {"alice", "heidi"}
        };

        for (String user : sampleUsers) {
            addUser(user);
        }

        for (String[] pair : friendships) {
            addFriendship(pair[0], pair[1]);
        }
    }

}
