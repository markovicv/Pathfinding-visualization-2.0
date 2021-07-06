package algorithms.factory;

import algorithms.*;
import algorithms.distance.ManhattanDistance;
import model.AlgorithmType;
import model.Node;

public class PathFindingAlgorithmFactory {


    public static PathfindingAlgorithm getPathFindingAlgorithm(String algorithmType, int pathFindingSpeed, Node[][] board,
                                                               Node startNode, Node endNode) {

        switch (algorithmType) {
            case AlgorithmType.BFS:
                return new Bfs(board, startNode, endNode, pathFindingSpeed);
            case AlgorithmType.DFS:
                return new Dfs(board, startNode, endNode, pathFindingSpeed);
            case AlgorithmType.DIJKSTRA:
                return new Dijkstra(board, startNode, endNode, pathFindingSpeed);
            default:
                return new Astar(board, startNode, endNode, new ManhattanDistance(), pathFindingSpeed);
        }


    }
}
