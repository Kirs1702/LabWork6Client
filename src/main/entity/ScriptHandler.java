package main.entity;

import main.ArgumentException;
import main.WrongScriptFileException;
import main.request.Request;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

public class ScriptHandler {



    public static void sendScript(SendReceiver sendReceiver, File file) throws IOException {

        RequestList reqList = new RequestList();

        try {
            reqList.readScriptFromFile(file);
        }
        catch (WrongScriptFileException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (ArgumentException e) {
            e.printStackTrace();
        }
        for (Request request : reqList){
            try {
                sendReceiver.send(request);
            } catch (SocketException ex) {
                System.out.println("Сервер недоступен! Завершение работы программы!");
                System.exit(0);
            }
            System.out.println(sendReceiver.receiveString());
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException ignored) {}

        }


    }
}



