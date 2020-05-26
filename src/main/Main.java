package main;

import main.entity.ConsoleReader;
import main.entity.History;
import main.entity.RequestList;
import main.entity.SendReceiver;
import main.request.Request;

import java.io.*;
import java.net.*;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {


        SendReceiver sendReceiver = null;
        try {
            sendReceiver = new SendReceiver(args[0], Integer.parseInt(args[1]));
        }catch (ConnectException ex) {
            System.out.println("Сервер недоступен! Завершение работы программы!");
            System.exit(0);
        }
        catch (UnknownHostException ex) {
            System.out.println("Неизвеестный сервер: " + args[0]);
            System.exit(0);
        }

        System.out.println("Клиент запущен.");
        History history = new History(10);

        Scanner scanner = new Scanner(System.in);

        String line;
        String command;
        String arg;




        while (true) {

            line = scanner.nextLine().trim().replaceAll("\\s+", " ").trim();
            history.capture(line);
            if (line.split(" ").length > 2) {
                System.out.println("Команда не может иметь больше одного аргумента!");
                continue;
            }

            command = ConsoleReader.prepareCommand(line);
            if (command == null){
                continue;
            }
            arg = ConsoleReader.prepareArg(line);

            String message = "Ошибка при вводе аргументов команды ";

            switch (command) {

                case "exit" : {
                    if (arg == null){
                        sendReceiver.closeConnection();
                        System.out.println("Завершение работы");
                        System.exit(0);
                    }
                    else {
                        System.out.println(message + command);
                        continue;
                    }
                }

                case "save" : {
                    if (arg == null){
                        System.out.println("Данная команда может быть выполнена только с сервера!");
                    }
                    else {
                        System.out.println(message + command);
                    }
                    continue;
                }

                case "execute_script" : {
                    if (Pattern.matches(".+", arg)){
                        RequestList reqList = new RequestList();

                        try {
                            reqList.readScriptFromFile(arg);
                        }
                        catch (IOException ex) {
                            System.out.println("Невозможно прочитать файл!");
                            continue;
                        }
                        catch (WrongScriptFileException ex) {
                            System.out.println("Данный скрипт или один из вложенных в него составлен неверно!");
                            continue;
                        }


                        for (Request request : reqList){
                            try {
                                sendReceiver.send(request);
                            } catch (SocketException ex) {
                                System.out.println("Сервер недоступен! Завершение работы программы!");
                                System.exit(0);
                            }
                            System.out.println(sendReceiver.receiveString());
                        }



                    }
                    else {
                        System.out.println(message + command);
                    }
                    continue;
                }

                case "history" : {
                    if (arg == null) {
                        history.show();
                    }
                    else {
                        System.out.println(message + command);
                    }
                    continue;
                }

                default : {
                    Request request = SendReceiver.prepareRequest(line);
                    if (request != null) {

                        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(request);*/

                        try {
                            sendReceiver.send(request);
                        } catch (SocketException ex) {
                            System.out.println("Сервер недоступен! Завершение работы программы!");
                            System.exit(0);
                        }
                        System.out.println(sendReceiver.receiveString());

                    }

                }
            }



        }


    }


}

