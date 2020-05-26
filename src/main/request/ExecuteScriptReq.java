package main.request;

public class ExecuteScriptReq  extends Request{

    private String scriptName;
    public ExecuteScriptReq(String scriptName) {
        super("execute_script");
        this.scriptName = scriptName;
    }

    public String getScriptName() {
        return scriptName;
    }
}
