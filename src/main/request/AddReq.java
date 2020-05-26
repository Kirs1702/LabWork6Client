package main.request;
public class AddReq extends Request {

    private String routeName;
    private float cordX;
    private int cordY;


    private boolean noAddFrom = false;
    private int fromX;
    private int fromY;
    private String fromName;
    private int toX;
    private  int toY;
    private  String toName;
    private int distance;


    public AddReq(){
        super("add");
    }

    public String getRouteName() {
        return routeName;
    }

    public float getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public boolean isNoAddFrom() {
        return noAddFrom;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public String getFromName() {
        return fromName;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public String getToName() {
        return toName;
    }

    public int getDistance() {
        return distance;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setCordX(float cordX) {
        this.cordX = cordX;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public void setNoAddFrom(boolean noAddFrom) {
        this.noAddFrom = noAddFrom;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
