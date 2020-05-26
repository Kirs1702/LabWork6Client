package main.request;

import java.io.Serializable;

public abstract class Request implements Serializable {

    private String name;

    public Request(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
