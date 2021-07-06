package algorithms.factory;

import algorithms.Astar;
import algorithms.Bfs;
import algorithms.Dfs;
import algorithms.PathfindingAlgorithm;
import algorithms.distance.ManhattanDistance;
import model.AlgorithmType;
import model.Node;

public class PathFindingAlgorithmFactory {


    public static PathfindingAlgorithm getPathFindingAlgorithm(String algorithmType, int pathFindingSpeed, Node[][] board,
                                                               Node startNode, Node endNode){
        if(algorithmType.equals(AlgorithmType.BFS)){
            return new Bfs(board,startNode,endNode,pathFindingSpeed);
        }
        if(algorithmType.equals(AlgorithmType.DFS))
            return new Dfs(board,startNode,endNode,pathFindingSpeed);
        return new Astar(board,startNode,endNode,new ManhattanDistance(),pathFindingSpeed);
    }
}
