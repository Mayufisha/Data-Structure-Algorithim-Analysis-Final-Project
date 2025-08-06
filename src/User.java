import java.util.*;
import java.util.stream.Collectors;

public class User {
    private String username;
    private Set<String> friends;

    public User(String username) throws IllegalArgumentException {
        if (!isValidUsername(username)) throw new IllegalArgumentException("Invalid username: " + username);
        this.username = sanitizeUsername(username);
        this.friends = new HashSet<>();
    }

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public static String sanitizeUsername(String username) {
        if (username == null) return null;
        return username.trim().toLowerCase();
    }

    public static boolean areValidUsernames(String... usernames) {
        for (String username : usernames) if (!isValidUsername(username)) return false;
        return true;
    }

    public static boolean isValidMenuChoice(String input, int min, int max) {
        try {
            int choice = Integer.parseInt(input.trim());
            return choice >= min && choice <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getFriends() {
        return new HashSet<>(friends);
    }

    public void addFriend(String friendUsername) {
        if (isValidUsername(friendUsername)) friends.add(sanitizeUsername(friendUsername));
    }

    public void removeFriend(String friendUsername) {
        if (isValidUsername(friendUsername)) friends.remove(sanitizeUsername(friendUsername));
    }

    public boolean isFriendsWith(String username) {
        if (!isValidUsername(username)) return false;
        return friends.contains(sanitizeUsername(username));
    }

    public int getFriendCount() {
        return friends.size();
    }

    @Override
    public String toString() {
        return username + " (" + friends.size() + " friends)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
