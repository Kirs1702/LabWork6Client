package main.entity;

import main.WrongScriptFileException;
import main.request.Request;

import java.io.*;
import java.util.ArrayList;

public class RequestList extends ArrayList<Request> {

    ScriptHistory sHistory = new ScriptHistory();


    public void readScriptFromFile(String path) throws IOException , WrongScriptFileException {
        sHistory.put(path);

        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));             //обработать


       String line;

       while ((line = reader.readLine()) != null) {

           if (line.startsWith("execute_script")) {
               String arg = ConsoleReader.prepareArg(line);

               if (!sHistory.put(arg)) {
                   throw new WrongScriptFileException("Ошибка в содержании файла скрипта! Замечена бесконечная рекурсия!");
               }
               else {
                   readScriptFromFile(arg);
               }

           }
           else {
               Request request = SendReceiver.prepareRequest(line);
               if (request == null) {
                   throw new WrongScriptFileException("Ошибка в содержании файла скрипта");
               }
               add(request);
           }

       }



    }

}
