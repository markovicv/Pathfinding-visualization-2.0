package algorithms;

import config.ApplicationConfiguration;
import model.Node;
import model.NodeType;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PathfindingAlgorithm implements Observable,Runnable {

    protected List<Observer> observers = new ArrayList<>();
    protected Node[][] board;
    protected Node start;
    protected Node end;

    private int[] X_DIR = {0,0,1,-1};
    private int[] Y_DIR = {1,-1,0,0};


    public PathfindingAlgorithm(Node[][] board,Node start,Node end){
        this.board = board;
        this.start = start;
        this.end = end;

    }

    @Override
    public void run() {
        start();
    }
    public abstract void start();


    protected boolean isFieldValid(Node node,int rowDir,int colDir){
        if(node.getRow()<0 || node.getRow()> ApplicationConfiguration.getInstance().getRowNumber())
            return false;
        if(node.getCol()<0 || node.getCol()>ApplicationConfiguration.getInstance().getRowNumber())
            return false;
        if(node.getRow()+rowDir<0 || node.getRow()+rowDir>ApplicationConfiguration.getInstance().getRowNumber())
            return false;
        if(node.getCol()+colDir < 0 || node.getCol()+colDir > ApplicationConfiguration.getInstance().getRowNumber())
            return false;
        if(board[node.getRow()+rowDir][node.getCol()+colDir].getNodeType() == NodeType.BLOCK)
            return false;
        return true;
    }

    protected List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for(int i=0;i<X_DIR.length;i++){
            if(isFieldValid(node,X_DIR[i],Y_DIR[i])){
                neighbors.add(board[node.getRow()+X_DIR[i]][node.getCol()+Y_DIR[i]]);
            }
        }
        return neighbors;
    }
    protected void findPath(Map<Node,Node> parents, Node end){
        Node tmpEnd = end;
        while(parents.containsKey(end)){
            end = parents.get(end);
            end.setNodeType(NodeType.PATH);
            sleep();
            notifyChange();
        }
        start.setNodeType(NodeType.START);
        tmpEnd.setNodeType(NodeType.END);
        notifyChange();
    }
    public void sleep(){
        try {
            Thread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if(observers.contains(observer))
            return;
        observers.add(observer);
    }

    @Override
    public void notifyChange() {
        for (Observer observer : observers)
            observer.fireUpdate();
    }

    @Override
    public void removeObserver(Observer observer) {
        if(observers.contains(observer))
            observers.remove(observer);
    }
}
