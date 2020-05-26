package main.entity;

import java.util.ArrayList;

public class ScriptHistory extends ArrayList<String> {
    /**
     * Метод, добавляющий в историю скриптов строку только если в строке нет эквиваленткой строки
     * @param line строка
     * @return true, если добавить удалось, false, если не удалось
     */
    public boolean put(String line) {
        if (!contains(line)) {
            add(line);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Метод, удаляющий последний элемент истории скриптов
     */
    public void pop() {
        remove(size()-1);
    }
}
