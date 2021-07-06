package algorithms;

import model.Node;
import model.NodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Dfs extends PathfindingAlgorithm{


    public Dfs(Node[][] board, Node start,Node end,int pathFindingSpeed){
        super(board,start,end,pathFindingSpeed);
    }


    @Override
    public void start() {
        startDfs();
    }

    private void startDfs(){
        Stack<Node> stack = new Stack();
        Map<Node,Node> parents = new HashMap();
        stack.push(start);

        while(!stack.isEmpty()){
            Node currentNode = stack.pop();
            currentNode.setVisited(true);

            if(currentNode==end){
                findPath(parents,end);
                return;
            }

            for(Node neighbor: getNeighbors(currentNode)){
                if(!neighbor.isVisited()){
                    parents.put(neighbor,currentNode);

                    if(neighbor==end)
                        neighbor.setNodeType(NodeType.END);
                    else
                        neighbor.setNodeType(NodeType.TO_VISIT);

                    stack.push(neighbor);
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
