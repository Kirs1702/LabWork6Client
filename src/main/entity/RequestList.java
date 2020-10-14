package main.entity;

import main.ArgumentException;
import main.WrongScriptFileException;
import main.request.Request;

import java.io.*;
import java.util.ArrayList;

public class RequestList extends ArrayList<Request> {

    ScriptHistory sHistory = new ScriptHistory();


    public void readScriptFromFile(File file) throws IOException, WrongScriptFileException, ArgumentException {
        sHistory.put(file.getPath());

        BufferedReader reader = new BufferedReader(new FileReader(file));


       String line;

       while ((line = reader.readLine()) != null) {

           if (line.startsWith("execute_script")) {
               String arg = ConsoleReader.prepareArg(line);

               if (!sHistory.put(arg)) {
                   throw new WrongScriptFileException("Ошибка в содержании файла скрипта! Замечена бесконечная рекурсия!");
               }
               else {
                   readScriptFromFile(new File(arg));
               }

           }
           else {
               Request request;
               try {
                   request = SendReceiver.prepareRequest(line);
               } catch (NumberFormatException | ArgumentException ex) {
                   request = null;
               }
               catch (StringIndexOutOfBoundsException ex) {
                   throw new WrongScriptFileException("Ошибка в содержании файла скрипта");
               }

               if (request == null) {
                   throw new WrongScriptFileException("Ошибка в содержании файла скрипта");
               }
               add(request);
           }

       }



    }

}
