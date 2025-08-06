import java.util.*;

public class GraphOperations {
    public static boolean areConnected(String user1, String user2, Map<String, User> users) {
        if (user1 == null || user2 == null) return false;
        user1 = user1.trim().toLowerCase();
        user2 = user2.trim().toLowerCase();

        if (!users.containsKey(user1) || !users.containsKey(user2)) return false;
        if (user1.equals(user2)) return true;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(user1);
        visited.add(user1);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : users.get(current).getFriends()) {
                if (neighbor.equals(user2)) return true;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }
}
