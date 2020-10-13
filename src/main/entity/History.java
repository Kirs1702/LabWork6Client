package main.entity;



public class History {
    private String[] lines;
    /**
     * Конструктор - создание истории определённой длины
     * @param len - длина
     */
    public History(int len){
        lines = new String[len];
        for (int i = 0; i<len; i++) {

        }
    }

    /**
     * Метод записи строки в историю
     * @param line - строка
     */
    public void capture(String line) {
        for (int i = 0; i<lines.length-1; i++) {
            lines[i] = lines[i+1];
        }
        lines[lines.length-1] = line;
    }

    /**
     * Метод вывода истории в стандартный поток вывода
     */
    public String get() {
        StringBuilder history = new StringBuilder();
        history.append("История введённых команд:\n");
        for (String line : lines) {
            if (line != null) {
                history.append(line).append("\n");
            }
        }
        return history.toString();
    }

}