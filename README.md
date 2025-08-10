### **Social Network Graph Explorer — Weighted Graph Version**

###### This Java console-based application simulates a social network using a **weighted graph** data structure.

###### Users are represented as **nodes**, and friendships are **undirected weighted edges**. The program allows users to add people, build friendships with **connection strengths (weights)**, explore connections, and analyze the overall network.

---

#### 🔧 Features

* ###### Add users and weighted friendships
* ###### Show a user’s friends with connection weights
* ###### Check direct or indirect (connected) relationships
* ###### Find mutual friends between two users
* ###### Suggest friends based on mutual connections
* ###### Show total users, total friendships, average/total edge weight, and most popular user(s)
* ###### Compute the lightest (minimum cost) path between two users using **Dijkstra’s algorithm**

---

#### &nbsp;Graph Model

* ###### Nodes: Users
* ###### Edges: Mutual friendships with weights (positive integers)
* ###### Graph Type: Undirected, weighted

---

#### &nbsp;📊 Algorithms Used

###### - **Dijkstra’s Algorithm**:
- To find the lightest (minimum cost) path between users

###### - **Breadth-First Search (BFS)**:
- To check if users are connected

###### - **Set Operations**:
- To find mutual friends
- To generate friend suggestions

---

#### 📁 Files

- **User.java** – User entity and validation
- **GraphOperations.java** – BFS, Dijkstra, pathfinding, mutuals, suggestions
- **NetworkStats.java** – Stats and analytics with weighted edges
- **SocialNetwork.java** – Business logic and data storage
- **SocialNetworkUI.java** – User interface and menu handling
- **Main.java** – Entry point

---


#### &nbsp;🚀 How to Run 

1. **Compile the program:**
```bash
javac *.java
    java Main