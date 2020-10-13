package main.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RouteSet implements Serializable {
    private LocalDateTime changeTime;
    private Set<Route> set;

    /**
     * Конструктор, при котором автоматически заполняется initTime (дата создания коллекции)
     */
    public RouteSet() {
        changeTime = LocalDateTime.now();
        HashSet<Route> hashSet = new HashSet<Route>();
        set = Collections.synchronizedSet(hashSet);
    }

    public int size() {
        return set.size();
    }

    public void add(Route route) {
        set.add(route);
        changeTime = LocalDateTime.now();
    }

    public void remove(Route route) {
        set.remove(route);
        changeTime = LocalDateTime.now();
    }

    public void clear() {
        set.clear();
        changeTime = LocalDateTime.now();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }

    public Set<Route> getSet() {
        return set;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    @Override
    public String toString() {
        return  "Тип коллекции: HashSet\n" +
                "Дата изменения: " + changeTime.toString() + "\n" +
                "Количество элементов: " + size();
    }
}
