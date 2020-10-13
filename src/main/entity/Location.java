package main.entity;

import java.io.Serializable;

public class Location implements Serializable {
    private String name; //Длина строки не должна быть больше 367, Поле может быть null
    private Integer x; //Поле не может быть null
    private Integer y; //Поле не может быть null

    public Location(Integer x, Integer y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}