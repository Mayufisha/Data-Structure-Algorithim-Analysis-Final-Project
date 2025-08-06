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

    public void displayStats() {
        System.out.println("\n=== Network Statistics ===");
        System.out.println("Total users: " + getTotalUsers());
        System.out.println("Total friendships: " + getTotalConnections());
        System.out.printf("Average friends per user: %.2f\n", getAverageFriendsPerUser());

        User mostPopular = getMostPopularUser();
        if (mostPopular != null) {
            System.out.println("Most popular user: " + mostPopular.getUsername() +
                    " (" + mostPopular.getFriendCount() + " friends)");
        }
    }
}
