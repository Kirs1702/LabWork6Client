package main.entity;



public class ConsoleReader {


    /**
     * Метот выделения первого слова из строки
     * @param line строка
     * @return возвращает название команды (первое слово)
     */
    public static String prepareCommand(String line) {
        line = line.trim().replaceAll("\\s+", " ").trim();
        try {
            line = (line + " ").split(" ")[0];     //Тут отрезаем первое слово
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return line;
    }


    /**
     * Метот выделения второго слова из строки
     * @param line строка
     * @return возвращает аргумент команды (второе слово)
     */
    public static String prepareArg(String line) {
        line = line.trim().replaceAll("\\s+", " ").trim();
        try {
            line = (line.concat(" ")).split(" ")[1];     //Тут отрезаем второе слово
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return line;
    }

    public static String cutFirst(String line) {
        line = line.trim().replaceAll("\\s+", " ").trim();
        line = line.substring(line.indexOf(' '));
        return line;
    }






}
