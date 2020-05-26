package main.entity;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleReader {

    Scanner scanner;

    public ConsoleReader(Scanner scanner, int historyLen) {
        this.scanner = scanner;
    }

    /**
     * Метот выделения названия команды из строки
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
     * Метот выделения названия команды из строки
     * @param line строка
     * @return возвращает аргумент команды (второе слово)
     */
    public static String prepareArg(String line) {
        line = line.trim().replaceAll("\\s+", " ").trim();
        try {
            line = (line.concat(" ")).split(" ")[1];     //Тут отрезаем second слово
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return line;
    }


    /**
     * Читает из сканнера строковое значение
     * @param canBeEmpty возможность строки быть пустой
     * @param limit максимальная длина строки, если 0 - неограничена
     * @return возвращает строку, соответствующую формату
     */
    public static String readStringValue(boolean canBeEmpty, int limit){
        Scanner scanner = new Scanner(System.in);
        String line;
        float lenLimit = (limit == 0) ?  Float.POSITIVE_INFINITY : limit;
        while (true) {
            System.out.print("Введите название"  + " (" + (canBeEmpty? "" : "не ") + "может быть пустым" + ((Float.isInfinite(lenLimit)) ? "" : ", максимальная длина: " + (int)lenLimit) + "): ");
            line = scanner.nextLine();
            if ((!canBeEmpty && Pattern.matches(" *", line)) || line.length() > lenLimit) {
                System.out.println("Неверно введено название.");
            } else {
                return Pattern.matches(" *", line) ? null : line.trim();
            }
        }
    }



    /**
     * Читает из сканнера целочисленное значение
     * @param description описание вводного значения
     * @return возвращает целое число типа Integer
     */
    public static Integer readIntValue(String  description){
        return readIntValueWithMinimum( -2147483648, description);
    }

    /**
     * Читает из сканнера целочисленное значение, ограниченное снизу
     * @param minimum минимальное допустимое значение
     * @param description описание вводного значения
     * @return возвращает целое число типа Integer больше минммального значения
     */
    public static Integer readIntValueWithMinimum(int minimum, String description){
        Scanner scanner = new Scanner(System.in);
        String line;
        Integer value = null;
        while (true) {
            System.out.print("Введите " + description + "(целое число, [" + minimum + "...2147483647]): ");
            line = scanner.nextLine();

            try {
                value = Integer.valueOf(line);
            }
            catch (NumberFormatException ignored) {}

            if (value != null && value >= minimum) {
                return  value;
            }
            System.out.println("Введено неверное значение.");
            value = null;
        }
    }

    /**
     * Читает из сканнера число с плавающей точкой
     * @param description описание вводного значения
     * @return возвращает число типа Float
     */
    public static Float readFloatValue(String description){
        Scanner scanner = new Scanner(System.in);
        String line;
        Float value = null;
        while (true) {
            System.out.print("Введите " + description +"(Число с плав. точкой, [ 1.4e-45f...3.4e+38f]): ");
            line = scanner.nextLine();
            try {
                value = Float.valueOf(line);
            }
            catch (NumberFormatException ignored){}
            if (value != null && Float.isFinite(value) && !Float.isNaN(value)) {
                return value;
            }
            System.out.println("Введено неверное значение.");
            value = null;
        }
    }

    /**
     * Читает из сканнера целочисленное значение
     * @return возвращает целое число типа Long
     */
    public static Long readLongValue(){
        Scanner scanner = new Scanner(System.in);
        String line;
        Long value = null;
        while (true) {
            System.out.print("Введите целое число, [-9223372036854775808...9223372036854775807L]: ");
            line = scanner.nextLine();

            try {
                value = Long.valueOf(line);
            }
            catch (NumberFormatException ignored) {}

            if (value != null) {
                return  value;
            }
            System.out.println("Введено неверное значение.");
            value = null;
        }
    }




}
