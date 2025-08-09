import java.util.*;
import java.util.stream.Collectors;

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

    public static List<String> findLightestPath(
            String src, String dst,
            Map<String, User> users,
            Map<String, Map<String, Integer>> adj) {

        if (src == null || dst == null) return null;
        src = src.trim().toLowerCase(); dst = dst.trim().toLowerCase();
        if (!users.containsKey(src) || !users.containsKey(dst)) return null;
        if (src.equals(dst)) return List.of(src);

        // Dijkstra
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (String u : users.keySet()) dist.put(u, Integer.MAX_VALUE);
        dist.put(src, 0); parent.put(src, null); pq.add(src);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (!adj.containsKey(u)) continue;
            if (u.equals(dst)) break;

            for (Map.Entry<String,Integer> e : adj.get(u).entrySet()) {
                String v = e.getKey();
                int w = e.getValue();
                if (w <= 0) continue; // skip bad weights
                int nd = dist.get(u) + w;
                if (nd < dist.get(v)) {
                    dist.put(v, nd);
                    parent.put(v, u);
                    pq.remove(v); // update key
                    pq.add(v);
                }
            }
        }

        if (dist.get(dst) == Integer.MAX_VALUE) return null;

        // reconstruct path
        LinkedList<String> path = new LinkedList<>();
        for (String at = dst; at != null; at = parent.get(at)) path.addFirst(at);
        return path;
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

    public static Set<String> findMutualFriends(String user1, String user2, Map<String, User> users) {
        if (user1 == null || user2 == null) return new HashSet<>();
        user1 = user1.trim().toLowerCase();
        user2 = user2.trim().toLowerCase();

        if (!users.containsKey(user1) || !users.containsKey(user2)) return new HashSet<>();

        Set<String> friends1 = users.get(user1).getFriends();
        Set<String> friends2 = users.get(user2).getFriends();
        return friends1.stream().filter(friends2::contains).collect(Collectors.toSet());
    }

    public static Set<String> suggestFriends(String username, Map<String, User> users) {
        if (username == null) return new HashSet<>();
        username = username.trim().toLowerCase();
        if (!users.containsKey(username)) return new HashSet<>();

        Set<String> directFriends = users.get(username).getFriends();
        Set<String> suggestions = new HashSet<>();
        Set<String> toExclude = new HashSet<>(directFriends);
        toExclude.add(username);

        for (String friend : directFriends) {
            if (users.containsKey(friend)) {
                for (String foaf : users.get(friend).getFriends()) {
                    if (!toExclude.contains(foaf)) {
                        suggestions.add(foaf);
                    }
                }
            }
        }
        return suggestions;
    }
}
