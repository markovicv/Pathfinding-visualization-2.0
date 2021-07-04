package algorithms.distance;

import model.Node;

public class ManhattanDistance implements Distance{

    @Override
    public int calculateDistance(Node node1, Node node2) {
        return Math.abs(node1.getRow()-node2.getRow()) + Math.abs(node1.getCol() - node2.getCol());
    }
}
