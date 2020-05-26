package main.request;

public class RemoveAllByDistanceReq extends Request {


    private int distance;
    public RemoveAllByDistanceReq(int distance) {
        super("remove_all_by_distance");
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
