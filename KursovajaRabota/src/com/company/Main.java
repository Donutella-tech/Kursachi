package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static   class Transportation  {

        static BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
        private Driver driver=new Driver();
        private  Truck truck=new Truck();
        private  Route route=new Route();

    public Transportation() throws IOException, SQLException {}

    public int mainMenu() throws IOException {
        System.out.println("1-Ввод данных");
        System.out.println("2-Вывод данных");
        System.out.println("3-Поиск данных");
        System.out.println("4-Сортировка данных");
        System.out.println("5-Удаление строк таблицы");
        System.out.println("6-Выход");

        int input=Integer.parseInt(reader.readLine());

        return input;
    }

    private int createMenu() throws IOException {
        System.out.println("1-Добавить нового водителя");
        System.out.println("2-Добавить новый автомобиль");
        System.out.println("3-Добавить новый маршрут");
        System.out.println("4-Назад");

        int input=Integer.parseInt(reader.readLine());

        return input;
    }
    private int outputMenu() throws IOException {
        System.out.println("1-Вывод имеющихся автомобилей");
        System.out.println("2-Вывод имеющихся водителей");
        System.out.println("3-Вывод маршрутов");
        System.out.println("4-Назад");
        int input=Integer.parseInt(reader.readLine());

        return input;
    }
    private int searchMenu()throws IOException {

        System.out.println("1-Поиск водителя");
        System.out.println("2-Поиск автомобиля");
        System.out.println("3-Поиск маршрута");
        System.out.println("4-Назад");
        int input=Integer.parseInt(reader.readLine());

        return input;

    }
    private int sortingMenu()throws IOException {

            System.out.println("1-Сортировка по прибыли(убывание)");
            System.out.println("2-Сортировка по статусу");
            System.out.println("3-Сортировка по длине маршрута(убывание)");
            System.out.println("4-Сортировка по автомобилям");
            System.out.println("5-Сортировка по объёму топлива");
            System.out.println("6-Назад");
            int input=Integer.parseInt(reader.readLine());

            return input;

        }
    private int deleteMenu()throws IOException {

            System.out.println("1-Удаление строки водителя");
            System.out.println("2-Удаление строки автомобиля");
            System.out.println("3-Удаление строки маршрута");
            System.out.println("4-Назад");
            int input=Integer.parseInt(reader.readLine());

            return input;

        }

    public void creation() throws IOException, SQLException {
        boolean cycle=true;
        while(cycle){
        int choice=createMenu();
    switch (choice)
    {
        case 1:
            driver.fullFIllDriver();
            break;
        case 2:
            truck.fullFillTruck();
            break;
        case 3:
            route.fullFillRoute();
            break;
        default:
            cycle=false;
            break;

    }
        }




    }
    public void showData()throws IOException, SQLException {
        boolean cycle=true;
        while(cycle) {
            int choice = outputMenu();
            switch (choice) {
                case 1:
                    truck.showTrucks();
                    break;
                case 2:
                    driver.showDrivers();
                    break;
                case 3:
                    route.showRoutes();
                    break;
                default:
                    cycle=false;
                    break;

            }
        }
    }
    public void searching() throws IOException, SQLException {
        boolean cycle=true;
        while(cycle) {
            int choice = searchMenu();
            switch (choice) {
                case 1:
                    driver.searchDriver();
                    break;
                case 2:
                    truck.searchTruck();
                    break;
                case 3:
                    route.searchRoute();
                    break;
                default:
                    cycle=false;
                    break;

            }
        }
    }
    public void sorting() throws IOException, SQLException {
        boolean cycle=true;
        while(cycle) {
            int choice = sortingMenu();
            switch (choice) {
                case 1:
                    route.sortByProfit();
                    break;
                case 2:
                    route.sortByStatus();
                    break;
                case 3:
                    route.sortByRouteDuration();
                    break;
                case 4:
                    route.sortByTruck();
                    break;
                case 5:
                    route.sortByCapacity();
                    break;
                default:
                    cycle=false;
                    break;

            }
        }
        }
    public void deletion() throws IOException, SQLException {
        boolean cycle=true;
        while(cycle) {
            int choice = deleteMenu();
            switch (choice) {
                case 1:
                    driver.deleteDriver();
                    break;
                case 2:
                    truck.deleteTruck();
                    break;
                case 3:
                    route.deleteRoute();
                    break;
                default:
                    cycle=false;
                    break;

            }
        }
        }


    }

    public static void main(String[] args) throws IOException, SQLException {

            Transportation transportation=new Transportation();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            }
            catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
             }
            boolean cycle=true;
            do {
                int choice = transportation.mainMenu();
                switch (choice) {
                    case 1:
                        transportation.creation();
                        break;
                    case 2:
                        transportation.showData();
                        break;
                    case 3:
                        transportation.searching();
                        break;
                    case 4:
                        transportation.sorting();
                        break;
                    case 5:
                        transportation.deletion();
                        break;
                    default:
                        cycle=false;
                        System.out.println("До свидания!!!");
                        break;
                }
            }while(cycle);

}

    public static Connection getConnection() throws  IOException, SQLException {

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties.txt"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }


    }



