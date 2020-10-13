package main.request;


import main.entity.Route;

public class AddIfMinReq extends AddReq{

    private long newId;

    public AddIfMinReq() {
        super();
        setName("add_if_min");

    }
    public AddIfMinReq(Route route) {
        super(route);
        setName("add_if_min");
    }

    public void setNewId(long newId) {
        this.newId = newId;
    }

    public long getNewId() {
        return newId;
    }
}
