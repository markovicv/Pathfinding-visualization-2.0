package algorithms;

import model.Node;
import model.NodeType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Bfs extends PathfindingAlgorithm{

    public Bfs(Node[][] board, Node start, Node end) {
        super(board, start, end);
    }


    @Override
    public void start() {
        startBfs();
    }

    private void startBfs(){
        Queue<Node> queue = new LinkedList<>();
        Map<Node,Node> parents = new HashMap<>();
        start.setVisited(true);
        queue.add(start);

        while(!queue.isEmpty()){
            Node currentNode = queue.poll();
            if(currentNode==end){
                findPath(parents,end);
                return;
            }
            for(Node neighbor:getNeighbors(currentNode)){
                if(!neighbor.isVisited()){
                    neighbor.setVisited(true);
                    parents.put(neighbor,currentNode);

                    if(neighbor==end)
                        neighbor.setNodeType(NodeType.END);
                    else
                        neighbor.setNodeType(NodeType.TO_VISIT);
                    queue.add(neighbor);
                }
            }
            notifyChange();
            sleep();
            if(currentNode!=start){
                currentNode.setNodeType(NodeType.VISITED);
            }
        }
    }


}
