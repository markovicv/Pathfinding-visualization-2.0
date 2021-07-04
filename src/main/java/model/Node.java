package model;

public class Node {

    /*
        Estimated movement cost to move from the given square to the final destination (square)
     */
    private int h;

    /*
        Shortest path value from starting node to given node
     */
    private int g;

    /*
        Sum of h(n) + g(n)
     */
    private int f;

    private int row;
    private int col;
    private boolean visited;
    private NodeType nodeType;

    public Node(int row,int col,NodeType nodeType){
        this.row = row;
        this.col = col;
        this.nodeType = nodeType;
        this.f = this.g = this.h = Integer.MAX_VALUE;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}
