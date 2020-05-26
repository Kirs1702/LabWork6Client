package main.entity;


import main.request.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SendReceiver {

    Socket gate;
    OutputStream os;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos;


    InputStream is;
    InputStreamReader isr;


    public SendReceiver(String host, int port) throws IOException {

        gate = new Socket(host, port);                                          //обработать исключение
        os = gate.getOutputStream();
        is = gate.getInputStream();
        isr = new InputStreamReader(is);
    }

    public <T extends Request> void send(T t) throws IOException {
        oos = new ObjectOutputStream(baos);
        oos.writeObject(t);                                      //обработать
        os.write(baos.toByteArray());
        baos.reset();
        baos.close();
        oos.close();
    }

    public static Request prepareRequest(String line) {
        if (Pattern.matches("\\s*", line)) {
            return null;
        }
        line = line.trim().replaceAll("\\s+", " ").trim();
        if (line.split(" ").length > 2) {
            System.out.println("Команда не может иметь больше одного аргумента!");
            return null;
        }
        String command = ConsoleReader.prepareCommand(line);
        String arg = ConsoleReader.prepareArg(line);


        String message = "Ошибка при вводе аргумента команды ";

        switch (command) {

            case "add_if_min" : {
                if (arg == null){
                    AddIfMinReq request = new AddIfMinReq();
                    request.setRouteName(ConsoleReader.readStringValue(false, 0));
                    request.setCordX(ConsoleReader.readFloatValue("x"));
                    request.setCordY(ConsoleReader.readIntValueWithMinimum(-862, "y"));
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Желаете добавить стартовую локацию?[y/n]: ");
                        String line2 = scanner.nextLine().trim();
                        if (line2.equalsIgnoreCase("y")) {
                            System.out.println("Стартовая локация: ");
                            request.setFromX(ConsoleReader.readIntValue("x"));
                            request.setFromY(ConsoleReader.readIntValue("y"));
                            request.setFromName(ConsoleReader.readStringValue(true, 367));
                            break;
                        }
                        else if (line2.equalsIgnoreCase("n")) {
                            request.setNoAddFrom(true);
                            break;
                        }
                    }
                    System.out.println("Конечная локация: ");
                    request.setToX(ConsoleReader.readIntValue("x"));
                    request.setToY(ConsoleReader.readIntValue("y"));
                    request.setToName(ConsoleReader.readStringValue(true, 367));
                    request.setDistance(ConsoleReader.readIntValueWithMinimum(1, "протяженность"));
                    request.setNewId(ConsoleReader.readLongValue());
                    return  request;
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case  "add" : {
                if (arg == null){
                    AddReq request = new AddReq();
                    request.setRouteName(ConsoleReader.readStringValue(false, 0));
                    request.setCordX(ConsoleReader.readFloatValue("x"));
                    request.setCordY(ConsoleReader.readIntValueWithMinimum(-862, "y"));
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Желаете добавить стартовую локацию?[y/n]: ");
                        String line2 = scanner.nextLine().trim();
                        if (line2.equalsIgnoreCase("y")) {
                            System.out.println("Стартовая локация: ");
                            request.setFromX(ConsoleReader.readIntValue("x"));
                            request.setFromY(ConsoleReader.readIntValue("y"));
                            request.setFromName(ConsoleReader.readStringValue(true, 367));
                            break;
                        }
                        else if (line2.equalsIgnoreCase("n")) {
                            request.setNoAddFrom(true);
                            break;
                        }
                    }
                    System.out.println("Конечная локация: ");
                    request.setToX(ConsoleReader.readIntValue("x"));
                    request.setToY(ConsoleReader.readIntValue("y"));
                    request.setToName(ConsoleReader.readStringValue(true, 367));
                    request.setDistance(ConsoleReader.readIntValueWithMinimum(1, "протяженность"));
                    return  request;
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "clear" : {
                if (arg == null){
                    return  new ClearReq();
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }            }

            case "filter_contains_name" : {
                if (arg != null && Pattern.matches(".*", arg)) {
                    return new FilterContainsNameReq(arg);
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "help" : {
                if (arg == null){
                    return  new HelpReq();
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "info" : {
                if (arg == null){
                    return  new InfoReq();
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "print_ascending" : {
                if (arg == null){
                    return  new PrintAscendingReq();
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "remove_all_by_distance" : {
                if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {
                    return new RemoveAllByDistanceReq(Integer.parseInt(arg));
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "remove_by_id" : {
                if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {
                    return new RemoveByIdReq(Long.parseLong(arg));
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "remove_greater" : {
                if (arg == null){

                    RemoveGreaterReq request = new RemoveGreaterReq();
                    request.setId(ConsoleReader.readLongValue());
                    return  request;
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "show" : {
                if (arg == null){
                    return  new ShowReq();
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "update" : {
                if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {

                    UpdateReq request = new UpdateReq(Long.parseLong(arg));
                    request.setRouteName(ConsoleReader.readStringValue(false, 0));
                    request.setCordX(ConsoleReader.readFloatValue("x"));
                    request.setCordY(ConsoleReader.readIntValueWithMinimum(-862, "y"));
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Желаете добавить стартовую локацию?[y/n]: ");
                        String line2 = scanner.nextLine().trim();
                        if (line2.equalsIgnoreCase("y")) {
                            System.out.println("Стартовая локация: ");
                            request.setFromX(ConsoleReader.readIntValue("x"));
                            request.setFromY(ConsoleReader.readIntValue("y"));
                            request.setFromName(ConsoleReader.readStringValue(true, 367));
                            break;
                        }
                        else if (line2.equalsIgnoreCase("n")) {
                            request.setNoAddFrom(true);
                            break;
                        }
                    }
                    System.out.println("Конечная локация: ");
                    request.setToX(ConsoleReader.readIntValue("x"));
                    request.setToY(ConsoleReader.readIntValue("y"));
                    request.setToName(ConsoleReader.readStringValue(true, 367));
                    request.setDistance(ConsoleReader.readIntValueWithMinimum(1, "протяженность"));
                    return  request;
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }
            }

            case "execute_script" : {
                if (arg != null && Pattern.matches(".*", arg)) {
                    return new ExecuteScriptReq(arg);
                }
                else {
                    System.out.println(message + command);
                    return  null;
                }

            }

            default : {
                System.out.println("Неизвестная команда: " + command);
                return null;
            }
        }


    }

    public String receiveString() throws IOException {
        byte[] b = new byte[8192];
        is.read(b);
        return new String(b);
    }

    public void closeConnection() throws IOException {
        gate.close();
    }


}
