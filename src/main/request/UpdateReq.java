package main.request;

public class UpdateReq extends AddReq{

    private Long id;

    public UpdateReq(Long id) {
        super();
        setName("update");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
