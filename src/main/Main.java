package main;

import main.entity.SendReceiver;
import main.gui.MyFrame;
import main.gui.ParametersDialog;
import main.gui.SendArgDialog;


import javax.swing.*;
import java.io.*;
import java.net.*;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        SendReceiver sendReceiver = null;
        try {
            sendReceiver = new SendReceiver(args[0], Integer.parseInt(args[1]));
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Требуется два аргумента командной строки!");
            System.exit(0);
        }
        catch (NumberFormatException ex){
            System.out.println("Неверно указан порт!");
            System.exit(0);
        }
        catch (ConnectException ex) {
            System.out.println("Сервер недоступен! Завершение работы программы!");
            System.exit(0);
        }
        catch (UnknownHostException ex) {
            System.out.println("Неизвеестный сервер: " + args[0]);
            System.exit(0);
        }


        new MyFrame(sendReceiver);





    }


}

