import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SocialNetworkUI {
    private SocialNetwork network;
    private Scanner scanner;

    public SocialNetworkUI() {
        this.network = new SocialNetwork();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\nSocial Network Graph Explorer Menu: ");

        System.out.println("1. Add user");
        System.out.println("2. Add friendship");
        System.out.println("3. Show user's friends");
        System.out.println("4. Check if users are connected");
        System.out.println("5. Find mutual friends");
        System.out.println("6. Get friend suggestions");
        System.out.println("7. Show all users");
        System.out.println("8. Show network statistics");
        System.out.println("9. Find shortest path between users");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleAddUser() {
        System.out.print("Enter username to add: ");
        String username = scanner.nextLine();

        if (network.addUser(username)) {
            System.out.println("User '" + User.sanitizeUsername(username) + "' added successfully!");
        } else {
            if (!User.isValidUsername(username)) {
                System.out.println("Error: Invalid username!");
            } else {
                System.out.println("Error: User already exists!");
            }
        }
    }

    private void handleAddFriendship() {
        System.out.print("Enter first user: ");
        String user1 = scanner.nextLine();
        System.out.print("Enter second user: ");
        String user2 = scanner.nextLine();
        System.out.print("Enter connection weight (positive integer): ");
        String wStr = scanner.nextLine().trim();

        int weight;
        try {
            weight = Integer.parseInt(wStr);
            if (weight <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Error: Weight must be a positive integer!");
            return;
        }

        if (network.addFriendship(user1, user2, weight)) {
            System.out.println("Friendship created between '" +
                    User.sanitizeUsername(user1) + "' and '" +
                    User.sanitizeUsername(user2) + "' with weight " + weight + "!");
        } else {
            if (!User.areValidUsernames(user1, user2)) {
                System.out.println("Error: Invalid usernames!");
            } else if (User.sanitizeUsername(user1).equals(User.sanitizeUsername(user2))) {
                System.out.println("Error: A user cannot be friends with themselves!");
            } else if (!network.userExists(user1) || !network.userExists(user2)) {
                System.out.println("Error: One or both users do not exist!");
            } else {
                System.out.println("Error: Users are already friends or weight invalid!");
            }
        }
    }

    private void handleShowFriends() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (!network.userExists(username)) {
            System.out.println("Error: User does not exist!");
            return;
        }

        Set<String> friends = network.getUserFriends(username);
        String sanitizedUsername = User.sanitizeUsername(username);

        if (friends.isEmpty()) {
            System.out.println("'" + sanitizedUsername + "' has no friends yet.");
        } else {
            System.out.println("'" + sanitizedUsername + "' is friends with: " +
                    String.join(", ", friends));
        }
    }

    private void handleCheckConnection() {
        System.out.print("Enter first user: ");
        String user1 = scanner.nextLine();
        System.out.print("Enter second user: ");
        String user2 = scanner.nextLine();

        if (!network.userExists(user1) || !network.userExists(user2)) {
            System.out.println("Error: One or both users do not exist!");
            return;
        }

        String sanitizedUser1 = User.sanitizeUsername(user1);
        String sanitizedUser2 = User.sanitizeUsername(user2);

        if (network.areDirectlyConnected(user1, user2)) {
            System.out.println("'" + sanitizedUser1 + "' and '" + sanitizedUser2 + "' are directly connected (friends).");
        } else if (network.areConnected(user1, user2)) {
            System.out.println("'" + sanitizedUser1 + "' and '" + sanitizedUser2 + "' are connected through mutual friends.");
        } else {
            System.out.println("'" + sanitizedUser1 + "' and '" + sanitizedUser2 + "' are not connected.");
        }
    }

    private void handleMutualFriends() {
        System.out.print("Enter first user: ");
        String user1 = scanner.nextLine();
        System.out.print("Enter second user: ");
        String user2 = scanner.nextLine();

        if (!network.userExists(user1) || !network.userExists(user2)) {
            System.out.println("Error: One or both users do not exist!");
            return;
        }

        Set<String> mutualFriends = network.findMutualFriends(user1, user2);
        String sanitizedUser1 = User.sanitizeUsername(user1);
        String sanitizedUser2 = User.sanitizeUsername(user2);

        if (mutualFriends.isEmpty()) {
            System.out.println("'" + sanitizedUser1 + "' and '" + sanitizedUser2 + "' have no mutual friends.");
        } else {
            System.out.println("Mutual friends of '" + sanitizedUser1 + "' and '" + sanitizedUser2 + "': " +
                    String.join(", ", mutualFriends));
        }
    }

    private void handleFriendSuggestions() {
        System.out.print("Enter username for suggestions: ");
        String username = scanner.nextLine();

        if (!network.userExists(username)) {
            System.out.println("Error: User does not exist!");
            return;
        }

        Set<String> suggestions = network.suggestFriends(username);
        String sanitizedUsername = User.sanitizeUsername(username);

        if (suggestions.isEmpty()) {
            System.out.println("No friend suggestions available for '" + sanitizedUsername + "'.");
        } else {
            System.out.println("Friend suggestions for '" + sanitizedUsername + "': " +
                    String.join(", ", suggestions));
        }
    }

    private void handleShowAllUsers() {
        Set<String> allUsers = network.getAllUsers();

        if (allUsers.isEmpty()) {
            System.out.println("No users in the network yet.");
            return;
        }

        System.out.println("All users in the network:");
        for (String user : allUsers) {
            int friendCount = network.getUserFriends(user).size();
            System.out.println("- " + user + " (" + friendCount + " friends)");
        }
    }

    private void handleShortestPath() {
        System.out.print("Enter starting user: ");
        String user1 = scanner.nextLine();
        System.out.print("Enter destination user: ");
        String user2 = scanner.nextLine();

        if (!network.userExists(user1) || !network.userExists(user2)) {
            System.out.println("Error: One or both users do not exist!");
            return;
        }

        List<String> path = network.findShortestPath(user1, user2);
        String sanitizedUser1 = User.sanitizeUsername(user1);
        String sanitizedUser2 = User.sanitizeUsername(user2);

        if (path == null || path.isEmpty()) {
            System.out.println("No connection path found between '" + sanitizedUser1 + "' and '" + sanitizedUser2 + "'.");
        } else if (path.size() == 1) {
            System.out.println("Same user provided!");
        } else {
            System.out.println("Shortest path from '" + sanitizedUser1 + "' to '" + sanitizedUser2 + "':");
            System.out.println(String.join(" → ", path));
            System.out.println("Distance: " + (path.size() - 1) + " degrees of separation");
        }
    }

    public void handleUserChoice(int choice) {
        switch (choice) {
            case 1: handleAddUser(); break;
            case 2: handleAddFriendship(); break;
            case 3: handleShowFriends(); break;
            case 4: handleCheckConnection(); break;
            case 5: handleMutualFriends(); break;
            case 6: handleFriendSuggestions(); break;
            case 7: handleShowAllUsers(); break;
            case 8: network.getStats().displayStats(network.getAdjacency()); break;
            case 9:
                handleShortestPath();
                break;
            case 0: System.out.println("Thank you for using Social Network Graph Explorer!"); break;
            default: System.out.println("Invalid option. Please try again.");
        }
    }


    public void run() {
        System.out.println("Welcome to Social Network Graph Explorer!");
        System.out.print("Would you like to load sample data? (y/n): ");
        String loadSample = scanner.nextLine().trim().toLowerCase();
        if (loadSample.equals("y") || loadSample.equals("yes")) {
            network.initializeSampleData();
            System.out.println("Sample data loaded successfully!");
        }

        int choice;
        do {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                handleUserChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = -1;
            }
        } while (choice != 0);

        scanner.close();
    }

}
