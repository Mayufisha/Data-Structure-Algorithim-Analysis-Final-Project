###  **Social Network Graph Explorer**



###### This Java console-based application simulates a simple social network using a graph data structure.

###### Users are represented as nodes, and friendships are undirected edges. The program allows users to add people, build friendships, explore connections, and analyze the overall network.



---



#### 🔧 Features



* ###### Add users and friendships
* ###### Show a user’s friends
* ###### Check direct or indirect (connected) relationships
* ###### Find mutual friends between two users
* ###### Suggest friends based on mutual connections
* ###### Show total users, total friendships, and most popular user
* ###### Compute the shortest path (degrees of separation) between users







#### &nbsp;Graph Model



* ###### Nodes: Users
* ###### Edges: Mutual friendships (undirected)
* ###### Graph Type: Undirected, unweighted





#### &nbsp;📊 Algorithms Used



###### \- Breadth-First Search (BFS):

###### &nbsp; - To check if users are connected

###### &nbsp; - To find the shortest path between users

###### \- Set operations:

###### &nbsp; - To find mutual friends

###### &nbsp; - To generate friend suggestions







#### &nbsp;🚀 How to Run



###### 1\. Compile the program:

###### &nbsp;  javac \*.java

###### 2\. Run it:

###### &nbsp;  java Main

###### Choose from the menu to interact with the social network.





#### 📁 Files



##### User.java – User entity and validation

##### GraphOperations.java – BFS, pathfinding, mutuals, suggestions

##### NetworkStats.java – Stats and analytics

##### SocialNetwork.java – Business logic and data storage

##### SocialNetworkUI.java – User interface and menu handling

##### Main.java – Entry point

