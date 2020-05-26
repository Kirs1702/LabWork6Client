package main.request;

public class RemoveGreaterReq extends Request{

    private long id;



    public RemoveGreaterReq() {
        super("remove_greater");

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
