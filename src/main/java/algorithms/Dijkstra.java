package algorithms;

import model.Node;
import model.NodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra extends PathfindingAlgorithm {

    public Dijkstra(Node[][] board, Node start, Node end, int pathfindingSpeed) {
        super(board, start, end, pathfindingSpeed);
    }

    @Override
    public void start() {
        startDijkstra();
    }

    private void startDijkstra() {
        Queue<Node> queue = new PriorityQueue();
        Map<Node, Node> parents = new HashMap();

        start.setG(0);
        start.setH(0);
        start.calculateF();
        start.setVisited(true);

        queue.add(start);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode == end) {
                findPath(parents, end);
                return;
            }
            for (Node neighbor : getNeighbors(currentNode)) {
                int gScoreTmp = currentNode.getG() + 1;
                if (gScoreTmp < neighbor.getG()) {
                    parents.put(neighbor, currentNode);
                    neighbor.setG(gScoreTmp);
                    neighbor.setH(0);
                    neighbor.calculateF();


                    if (!neighbor.isVisited()) {
                        neighbor.setVisited(true);
                        if (neighbor == end)
                            neighbor.setNodeType(NodeType.END);
                        else
                            neighbor.setNodeType(NodeType.TO_VISIT);
                        queue.add(neighbor);
                    }
                }

            }
            notifyChange();
            sleep();
            if (currentNode != start) {
                currentNode.setNodeType(NodeType.VISITED);
            }
        }

    }
}
