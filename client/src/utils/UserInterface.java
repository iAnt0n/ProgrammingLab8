package utils;

import collection.*;
import gui.MainJFrame;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

/**
 * Класс, служащий для запроса ввода и его обработки, вывода сообщений
 */
public class UserInterface {
    private Writer writer;
    private Scanner scanner;
    private boolean interactive;

    /**
     * Конструктор пользовательского интерфейса
     * @param reader символьный поток, из которого будут считываться введеные данные
     * @param writer символьный поток для вывода сообщений
     * @param interactive определяет режим работы интерфейса
     */
    public UserInterface(Reader reader, Writer writer, boolean interactive) {
        this.writer = writer;
        this.scanner = new Scanner(reader);
        this.interactive = interactive;
    }

    /**
     * Выводит в указанный в конструкторе поток вывода строку
     * @param message строка, которую необходимо вывести
     */
    public synchronized void write(String message) {
            MainJFrame.resultTextArea.setText(message);
    }

    /**
     * Выводит в указанный в конструкторе поток вывода строку и переносит каретку
     * @param message строка, которую необходимо вывести
     */
    public synchronized void writeln(String message){
        write(message+"\n");
    }

    private String readString(String msg){
        if (interactive) {
            String result = "";
            writeln(msg);
            while (result.isEmpty()) {
                result = scanner.nextLine().trim();
                if (result.isEmpty()) {
                    writeln("Введите непустую строку");
                }
            }
            return result;
        }
        else return scanner.nextLine();
    }

    /**
     * Выводит сообщение для запроса ввода,
     * считывает из указанного в конструкторе потока ввода число типа Double,
     * проверяет его на попадание в заданный интервал и на соответствие числу знаков
     * @param msg строка для запроса ввода
     * @param min нижняя граница для числа
     * @param max верхняя граница для числа
     * @param digits максимальное число знаков числа
     * @return число типа Double
     */
    private Double readDouble(String msg, Number min, Number max, int digits){
        if (interactive) {
            writeln(msg);
            Double result = null;
            while (result == null) {
                try {
                    String input = scanner.nextLine().trim();
                    char[] digs = input.toCharArray();
                    int counter = 0;
                    for (char s: digs){
                        if (Character.isDigit(s)) counter++;
                    }
                    double d = Double.parseDouble(input);
                    if (checkDouble(d, min, max)&&counter<=digits) {
                        result = d;
                    } else {
                        writeln("Неверный ввод. Повторите");
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    writeln("Неверный ввод. Повторите");
                }
            }
            return result;
        }
        else return Double.parseDouble(scanner.nextLine());
    }

    /**
     * Выводит сообщение для запроса ввода,
     * считывает из указанного в конструкторе потока ввода число типа Long,
     * проверяет его на попадание в заданный интервал
     * @param msg строка для запроса ввода
     * @param min нижняя граница для числа
     * @param max верхняя граница для числа
     * @return число типа Long
     */
    private Long readLong(String msg, Number min, Number max){
        if(interactive) {
            writeln(msg);
            Long result = null;
            while (result == null) {
                try {
                    long l = Long.parseLong(scanner.nextLine().trim());
                    if (checkLong(l, min, max)) {
                        result = l;
                    } else {
                        writeln("Неверный ввод. Повторите");
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    writeln("Неверный ввод. Повторите");
                }
            }
            return result;
        }
        else return Long.parseLong(scanner.nextLine());
    }

    /**
     * Выводит список элементов {@link Climate} и приглашение к вводу,
     * считывает из указанного в конструкторе потока ввода значение Climate
     * @return одна из констант {@link Climate}
     */
    private Climate readClimate() {
        if(interactive) {
            while (true) {
                String climate = scanner.nextLine().trim();
                if (climate.isEmpty()) {
                    return null;
                }
                switch (climate.toUpperCase()) {
                    case "MONSOON":
                        return Climate.MONSOON;
                    case "HUMIDCONTINENTAL":
                        return Climate.HUMIDCONTINENTAL;
                    case "OCEANIC":
                        return Climate.OCEANIC;
                    case "MEDITERRANIAN":
                        return Climate.MEDITERRANIAN;
                }
            }
        }
        else return Climate.valueOf(scanner.nextLine().toUpperCase());
    }

    /**
     * Выводит список элементов {@link Government} и приглашение к вводу,
     * считывает из указанного в конструкторе потока ввода значение Government
     * @return одна из констант {@link Government}
     */
    private Government readGovernment() {
        if(interactive) {
            while (true) {
                String gov = scanner.nextLine().trim();
                if (gov.isEmpty()) {
                    writeln("Режим должен быть известен");
                    continue;
                }
                switch (gov.toUpperCase()) {
                    case "GERONTOCRACY":
                        return Government.GERONTOCRACY;
                    case "DESPOTISM":
                        return Government.DESPOTISM;
                    case "THALASSOCRACY":
                        return Government.THALASSOCRACY;
                }
            }
        }
        else return Government.valueOf(scanner.nextLine().toUpperCase());
    }

    /**
     * Выводит список элементов {@link StandardOfLiving} и приглашение к вводу,
     * считывает из указанного в конструкторе потока ввода значение StandardOfLiving
     * @return одна из констант {@link StandardOfLiving}
     */
    private StandardOfLiving readStandardOfLiving() {
        if (interactive) {
            while (true) {
                String sol = scanner.nextLine().trim();
                if (sol.isEmpty()) {
                    return null;
                }
                switch (sol.toUpperCase()) {
                    case "ULTRA_HIGH":
                        return StandardOfLiving.ULTRA_HIGH;
                    case "LOW":
                        return StandardOfLiving.LOW;
                    case "NIGHTMARE":
                        return StandardOfLiving.NIGHTMARE;
                }

            }
        }
        else return StandardOfLiving.valueOf(scanner.nextLine().toUpperCase());
    }

    /**
     * Считывает из потока значения полей класса {@link Human} и конструирует объект с этими значениями
     * @return объект класса {@link Human}
     */
    public Human readHuman(){
        String name = scanner.nextLine();
        int age = Integer.parseInt(scanner.nextLine());
        Double height = Double.parseDouble(scanner.nextLine());
        return new Human(name, age, height);
    }

    /**
     * Считывает из потока значения полей класса {@link Coordinates} и конструирует объект с этими значениями
     * @return объект класса {@link Coordinates}
     */
    private Coordinates readCoordinates(){
        Integer x = Integer.parseInt(scanner.nextLine());
        Double y = Double.parseDouble(scanner.nextLine());
        return new Coordinates(x, y);
    }

    /**
     * Считывает из потока значения полей класса {@link City} и конструирует объект с этими значениями
     * @return объект класса {@link City}
     */
    public City readCity(){
        String name = scanner.nextLine();
        Coordinates coordinates = readCoordinates();
        float area = Float.parseFloat(scanner.nextLine());
        Long population = Long.parseLong(scanner.nextLine());
        Float metersAboveSeaLevel = Float.parseFloat(scanner.nextLine());
        Climate climate = readClimate();
        Government government = readGovernment();
        StandardOfLiving standardOfLiving = readStandardOfLiving();
        Human governor = readHuman();
        return new City(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    /**
     * Проверяет число типа long на попадание в заданный интервал
     * @param s число, которое необходимо проверить
     * @param min нижняя граница интервала
     * @param max верхняя граница интервала
     * @return true, если число попало в интервал, иначе false
     */
    private boolean checkLong(long s, Number min, Number max) {
            return s>min.longValue()&&s<max.longValue();
    }

    /**
     * Проверяет число типа double на попадание в заданный интервал
     * @param s число, которое необходимо проверить
     * @param min нижняя граница интервала
     * @param max верхняя граница интервала
     * @return true, если число попало в интервал, иначе false
     */
    private boolean checkDouble(double s, Number min, Number max) {
            return s>min.doubleValue()&&s<max.doubleValue();
    }

    public boolean hasNextLine(){
        return scanner.hasNextLine();
    }

    public String readLine(){
        return scanner.nextLine();
    }

    public String readLineWithMessage(String message) {
        write(message);
        return readLine();
    }

    public String read(){
        return scanner.nextLine();
    }
}
