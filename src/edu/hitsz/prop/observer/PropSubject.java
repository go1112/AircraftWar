package edu.hitsz.prop.observer;

public interface PropSubject {
    void addObserver(EnemyObserver observer);
    void removeObserver(EnemyObserver observer);
    void notifyObservers();
}
