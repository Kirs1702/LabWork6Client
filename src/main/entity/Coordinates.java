package main.entity;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x; //Поле не может быть null
    private Integer y; //Значение поля должно быть больше -863, Поле не может быть null

    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}