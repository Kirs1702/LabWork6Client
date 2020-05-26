package main.request;

public class FilterContainsNameReq extends Request{

    private String routeName;
    public FilterContainsNameReq(String routeName) {
        super("filter_contains_name");
        this.routeName = routeName;
    }


    public String getRouteName() {
        return routeName;
    }
}
