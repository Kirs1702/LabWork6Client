package main.request;

import main.entity.Route;

public class UpdateReq extends AddReq{

    private Long id;

    public UpdateReq(Long id) {
        super();
        setName("update");
        this.id = id;
    }

    public UpdateReq() {
        super();
        setName("update");
    }

    public UpdateReq(Long id, Route route) {
        super(route);
        setName("update");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
