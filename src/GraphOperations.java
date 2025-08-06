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

    public static List<String> findShortestPath(String user1, String user2, Map<String, User> users) {
        if (user1 == null || user2 == null) return null;
        user1 = user1.trim().toLowerCase();
        user2 = user2.trim().toLowerCase();

        if (!users.containsKey(user1) || !users.containsKey(user2)) return null;
        if (user1.equals(user2)) return Arrays.asList(user1);

        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(user1);
        visited.add(user1);
        parent.put(user1, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : users.get(current).getFriends()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    if (neighbor.equals(user2)) {
                        List<String> path = new ArrayList<>();
                        for (String at = user2; at != null; at = parent.get(at)) {
                            path.add(at);
                        }
                        Collections.reverse(path);
                        return path;
                    }
                    queue.offer(neighbor);
                }
            }
        }
        return null;
    }
}
