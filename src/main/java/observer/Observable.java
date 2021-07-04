package observer;

public interface Observable {

    void addObserver(Observer observer);
    void notifyChange();
    void removeObserver(Observer observer);
}
