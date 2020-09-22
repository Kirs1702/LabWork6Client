package main.request;

public class LoginReq extends Request {
    private String username;
    private String password;
    private boolean register;

    public LoginReq() {
        super("login");
        register = false;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }
}
