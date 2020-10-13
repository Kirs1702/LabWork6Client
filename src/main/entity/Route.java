package main.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Route implements Comparable<Route>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private Integer distance; //Поле не может быть null, Значение поля должно быть больше 1
    private String user;


    /**
     * Стандартный конструктор, поля id и creationDate заполняются автоматически
     * @param name имя
     * @param coordinates объект типа Coordinates
     * @param from объект типа Location
     * @param to объект типа Location
     * @param distance протяженность
     */
    public Route (String name, Coordinates coordinates, Location from, Location to, Integer distance) {
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route (String name, Coordinates coordinates, Location to, Integer distance) {
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.from = null;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Конструктор, в котором все поля заполняются пользователем
     * @param id id
     * @param name имя
     * @param coordinates объект типа Coordinates
     * @param year год
     * @param month месяц
     * @param day день
     * @param hours часы
     * @param minutes минуты
     * @param seconds секунды
     * @param from объект типа Location
     * @param to объект типа Location
     * @param distance протяженность
     */
    public Route (long id, String name, Coordinates coordinates,  int year, int month, int day, int hours, int minutes, int seconds, Location from, Location to, Integer distance) {
        this.creationDate = LocalDateTime.of(year, month, day, hours, minutes, seconds);
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route (long id, String name, Coordinates coordinates,  LocalDateTime creationDate, Location from, Location to, Integer distance) {
        this.creationDate = creationDate;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Route (long id, String name, Coordinates coordinates, Location from, Location to, Integer distance) {
        this.creationDate = LocalDateTime.now();
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Route (long id, String name, Coordinates coordinates, Location to, Integer distance) {
        this.creationDate = LocalDateTime.now();
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = null;
        this.to = to;
        this.distance = distance;
    }


    public void copyRoute(Route newRoute){
        setId(newRoute.getId());
        setName(newRoute.getName());
        setCoordinates(newRoute.getCoordinates());
        setCreationDate(newRoute.getCreationDate());
        setFrom(newRoute.getFrom());
        setTo(newRoute.getTo());
        setDistance(newRoute.getDistance());
        setUser(newRoute.getUser());

    }




    /**
     * Конструктор, в котором автоматически заполняются поля id и creationDate
     */
    public Route () {
        this.creationDate = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDistance() {
        return distance;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    /**
     * Метод для сравнения объектов типа Route по полю id
     * @param o объект для сравнения
     * @return возвращает 1 если id1 больше id2, -1 если id1 меньше id2, 0 если id1 равно id2
     */
    @Override
    public int compareTo(Route o) {
        if (id>o.getId()) return 1;
        else if (id<o.getId()) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Route{" +
                "user='" + user + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }


}
