package algorithms;

import model.Node;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class PathfindingAlgorithm implements Observable,Runnable {

    protected List<Observer> observers = new ArrayList<>();
    protected Node[][] board;
    protected Node start;
    protected Node end;


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
