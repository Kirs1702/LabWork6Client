package main.request;

public class RemoveByIdReq extends Request {

    private Long id;

    public RemoveByIdReq(Long id) {
        super("remove_by_id");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
