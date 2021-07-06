package algorithms.distance;

import model.Node;

public class EuclideanDistance implements Distance {

    @Override
    public int calculateDistance(Node node1, Node node2) {
        return (int)Math.sqrt(Math.pow(node1.getRow()-node2.getRow(),2) + Math.pow(node1.getCol()-node2.getCol(),2));
    }
}
