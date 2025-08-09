import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworkStats {
    private Map<String, User> users;

    public NetworkStats(Map<String, User> users) {
        this.users = users;
    }

    public int getTotalUsers() {
        return users.size();
    }

    public int getTotalConnections() {
        return users.values().stream().mapToInt(User::getFriendCount).sum() / 2;
    }

    public double getAverageFriendsPerUser() {
        if (users.isEmpty()) return 0.0;
        return (double) (getTotalConnections() * 2) / users.size();
    }

    public User getMostPopularUser() {
        return users.values().stream().max(Comparator.comparing(User::getFriendCount)).orElse(null);
    }

    public List<User> getUsersByPopularity() {
        return users.values().stream()
                .sorted(Comparator.comparing(User::getFriendCount).reversed())
                .collect(Collectors.toList());
    }

    public int getTotalEdgeWeight(Map<String, Map<String,Integer>> adj) {
        // sum all weights divided by 2 (undirected stored twice)
        int sum = 0;
        for (var m : adj.values()) for (int w : m.values()) sum += w;
        return sum / 2;
    }

    public double getAverageEdgeWeight(Map<String, Map<String,Integer>> adj) {
        int edges = getTotalConnections();
        if (edges == 0) return 0.0;
        return (double) getTotalEdgeWeight(adj) / edges;
    }

    public void displayStats(Map<String, Map<String,Integer>> adj) {
        System.out.println("\n=== Network Statistics ===");
        System.out.println("Total users: " + getTotalUsers());
        System.out.println("Total friendships: " + getTotalConnections());
        System.out.printf("Average friends per user: %.2f\n", getAverageFriendsPerUser());
        System.out.println("Total edge weight: " + getTotalEdgeWeight(adj));
        System.out.printf("Average edge weight: %.2f\n", getAverageEdgeWeight(adj));

        // (optional) print all most-popular users (ties)
        int maxFriends = users.values().stream().mapToInt(User::getFriendCount).max().orElse(0);
        List<String> tops = users.values().stream()
                .filter(u -> u.getFriendCount() == maxFriends)
                .map(User::getUsername)
                .sorted().toList();
        if (!tops.isEmpty()) {
            System.out.println("Most popular user(s): " + String.join(", ", tops)
                    + " (" + maxFriends + " friends)");
        }
    }

}
