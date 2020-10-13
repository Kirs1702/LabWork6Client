package main.entity;


import main.ArgumentException;
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

        gate = new Socket(host, port);
        os = gate.getOutputStream();
        is = gate.getInputStream();
        isr = new InputStreamReader(is);
    }

    public <T extends Request> void send(T t) throws IOException {
        oos = new ObjectOutputStream(baos);
        oos.writeObject(t);
        os.write(baos.toByteArray());
        baos.reset();
        baos.close();
        oos.close();
    }

    public static Request prepareRequest(String line) throws ArgumentException {
        if (Pattern.matches("\\s*", line)) {
            return null;
        }
        line = line.trim().replaceAll("\\s+", " ").trim();

        String command = ConsoleReader.prepareCommand(line);
        String arg = ConsoleReader.prepareArg(line);


        String message = "Ошибка при вводе аргумента команды ";

        switch (command) {

            case "add_if_min" : {
                /*if (arg == null){
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
                    throw new ArgumentException("Неверный аргумент команды!");
                }*/

                String pastLine = ConsoleReader.cutFirst(line);
                AddIfMinReq request = new AddIfMinReq();
                request.setRouteName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordX(Float.parseFloat(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);


                request.setFromName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);

                request.setToName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setDistance(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setNewId(Long.parseLong(ConsoleReader.prepareCommand(pastLine)));

                if((request.getCordY() < -861) || request.getFromName().length() > 366 || request.getToName().length() > 366 || request.getDistance() <= 1) {
                    throw new ArgumentException("Неверно составлен файл!");
                }
                return  request;

            }

            case  "add" : {
                /*if (arg == null){
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
                    throw new ArgumentException("Неверный аргумент команды!");
                }*/
                String pastLine = ConsoleReader.cutFirst(line);
                AddReq request = new AddReq();
                request.setRouteName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordX(Float.parseFloat(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);


                request.setFromName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);

                request.setToName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setDistance(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));

                if((request.getCordY() < -861) || request.getFromName().length() > 366 || request.getToName().length() > 366 || request.getDistance() <= 1) {
                    throw new ArgumentException("Неверно составлен файл!");
                }
                return  request;
            }

            case "clear" : {
                if (arg == null){
                    return  new ClearReq();
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
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
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "info" : {
                if (arg == null){
                    return  new InfoReq();
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "print_ascending" : {
                if (arg == null){
                    return  new PrintAscendingReq();
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "remove_all_by_distance" : {
                if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {
                    return new RemoveAllByDistanceReq(Integer.parseInt(arg));
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "remove_by_id" : {
                if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {
                    return new RemoveByIdReq(Long.parseLong(arg));
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "remove_greater" : {
                if (arg != null){
                    RemoveGreaterReq request = new RemoveGreaterReq();
                    try {
                        request.setId(Long.parseLong(arg));
                    }
                    catch (NumberFormatException ex) {
                        throw new ArgumentException("Неверный аргумент команды!");
                    }
                    return  request;
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "show" : {
                if (arg == null){
                    return  new ShowReq();
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }
            }

            case "update" : {
                /*if (arg != null && Pattern.matches("[0-9]{1,10}", arg)) {

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
                    throw new ArgumentException("Неверный аргумент команды!");
                }*/
                String pastLine = ConsoleReader.cutFirst(line);
                UpdateReq request = new UpdateReq();
                request.setRouteName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordX(Float.parseFloat(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setCordY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);


                request.setFromName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setFromY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);

                request.setToName(ConsoleReader.prepareCommand(pastLine));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToX(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setToY(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setDistance(Integer.parseInt(ConsoleReader.prepareCommand(pastLine)));
                pastLine = ConsoleReader.cutFirst(pastLine);
                request.setId(Long.parseLong(ConsoleReader.prepareCommand(pastLine)));

                if((request.getCordY() < -861) || request.getFromName().length() > 366 || request.getToName().length() > 366 || request.getDistance() <= 1) {
                    throw new ArgumentException("Неверно составлен файл!");
                }
                return  request;
            }

            case "execute_script" : {
                if (arg != null && Pattern.matches(".*", arg)) {
                    return new ExecuteScriptReq(arg);
                }
                else {
                    throw new ArgumentException("Неверный аргумент команды!");
                }

            }

            default : {
                throw new ArgumentException("Неизвестная команда!");
            }
        }


    }

    public String receiveString() throws IOException {
        byte[] b = new byte[8192];
        is.read(b);
        return new String(b);
    }

    public RouteSet receiveRouteSet() throws IOException, ClassNotFoundException {
        byte[] b = new byte[32768];
        is.read(b);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b));
        Object o = ois.readObject();
        return (RouteSet) o;

    }

    public void closeConnection() throws IOException {
        gate.close();
    }

    public Byte receiveByte() throws IOException {
        byte[] b = new byte[16];
        is.read(b);
        return b[0];
    }
}
