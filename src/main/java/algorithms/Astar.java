package algorithms;

import algorithms.distance.Distance;
import model.Node;
import model.NodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar extends PathfindingAlgorithm{

    private Distance distance;

    public Astar(Node[][] board, Node start, Node end,Distance distance) {
        super(board, start, end);
        this.distance = distance;
    }


    @Override
    public void start() {
        startAstar();
    }
    private void startAstar(){
        System.out.println("POCEO ASTARRRRR");
        Queue<Node> queue = new PriorityQueue();
        Map<Node,Node> parents = new HashMap();
        queue.add(start);

        start.setG(0);
        start.setH(distance.calculateDistance(start,end));
        start.calculateF();

        while (!queue.isEmpty()){
            Node currentNode = queue.poll();
            currentNode.setVisited(true);

            if(currentNode == end){
                findPath(parents,end);
                return;
            }
            for(Node neighbor:getNeighbors(currentNode)){
                int currentGScore = currentNode.getG()+1;

                if(currentGScore<neighbor.getG()){
                    parents.put(neighbor,currentNode);
                    neighbor.setG(currentGScore);
                    neighbor.setH(distance.calculateDistance(neighbor,end));
                    neighbor.calculateF();

                    if(!neighbor.isVisited()){
                        if(neighbor==end)
                            neighbor.setNodeType(NodeType.END);
                        else
                            neighbor.setNodeType(NodeType.TO_VISIT);
                        queue.add(neighbor);
                    }
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
