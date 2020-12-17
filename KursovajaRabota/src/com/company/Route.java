package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Route  {
    private String fuelGrade;
    private String status;
    private double profit;
    private double routeDuration;
    private int capacity;
    private int id;
    Connection connection = Main.getConnection();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public Route() throws IOException, SQLException {}
    public void fullFillRoute() throws IOException, SQLException {

        fuelGrade = fuelGradeCheck();
        routeDuration = routeDurationCheck();
        profit = profitCheck();
        status = statusCheck();
        capacity=fuelCapacityCheck();
        Connection connection = Main.getConnection();
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        Truck truck=new Truck();
        truck.showTrucks();

        try{
            System.out.println("Выберете необходимый автомобиль(id)");
            int truck_id=Integer.parseInt(reader.readLine());
            String input="INSERT routes(fuelGrade,routeDuration,truck_id,profit,status,capacity) VALUES ("+"\'" + fuelGrade+"\',\'"+routeDuration+"\',\'"+truck_id+"\',\'"+profit+"\',\'"+status+"\',\'"+capacity+"\')";
            statement.executeUpdate(input);
        }

        catch (NumberFormatException e)
        {
            System.out.println("Введено не число");
        }


    }
    public void showRoutes() throws SQLException {
        String sql="SELECT * FROM routes";
        Statement statement2 = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Получаем запись...");
        while (resultSet.next()) {


            id = resultSet.getInt("id");
            int truck_id=resultSet.getInt("truck_id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");
            String sql2="SELECT * FROM trucks WHERE id = "+"\'"+truck_id+"\'";

            ResultSet resultSet2 = statement2.executeQuery(sql2);
            resultSet2.first();


            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);

            String carName = resultSet2.getString("carName");
            String carPlateNumber = resultSet2.getString("carPlateNum");
            int yearOfCar=resultSet2.getInt("yearOfCar");
            System.out.println("Название автомобиля: " + carName);
            System.out.println("Номерной знак:" + carPlateNumber);
            System.out.println("Год выпуска авто: " + yearOfCar);

            System.out.println("\n=========================\n");

        }

    }
    public void searchRoute() throws SQLException, IOException {

        System.out.println("Введите статус перевозки: ");

        String search= reader.readLine();

        String sql="SELECT * FROM routes WHERE status LIKE \"%"+search+"%\"";

        Statement statement2 = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Получаем запись...");
        while (resultSet.next()) {


            id = resultSet.getInt("id");
            int truck_id=resultSet.getInt("truck_id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");
            String sql2="SELECT * FROM trucks WHERE id = "+"\'"+truck_id+"\'";

            ResultSet resultSet2 = statement2.executeQuery(sql2);
            resultSet2.first();


            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);

            String carName = resultSet2.getString("carName");
            String carPlateNumber = resultSet2.getString("carPlateNum");
            int yearOfCar=resultSet2.getInt("yearOfCar");
            System.out.println("Название автомобиля: " + carName);
            System.out.println("Номерной знак:" + carPlateNumber);
            System.out.println("Год выпуска авто: " + yearOfCar);

            System.out.println("\n=========================\n");

        }



    }
    private double profitCheck() {
        boolean check;
        double input=0;
        do {
            check=false;
            try {
                System.out.println("Введите сумму перевозки(BYN):");
                input = Double.parseDouble(reader.readLine());

            } catch (NumberFormatException | IOException e) {

                System.out.println("Введено не число. Повторите ввод");
                check=true;
            }
        }while(check);

        return input;
    }
    private String fuelGradeCheck() throws IOException {
        String fuelGrade = "";
        boolean check;
        do {
            System.out.println("Выберете тип перевозимого топлива:");
            System.out.println("1-Дизель");
            System.out.println("2-АИ-92");
            System.out.println("3-АИ-95");
            System.out.println("4-АИ-98");
            System.out.println("5-АИ-100");
            int input = Integer.parseInt(reader.readLine());
            check=false;

            switch (input) {
                case 1:
                    fuelGrade = "Дизель";
                    break;
                case 2:
                    fuelGrade = "АИ-92";
                    break;
                case 3:
                    fuelGrade = "АИ-95";
                    break;
                case 4:
                    fuelGrade = "АИ-98";
                    break;
                case 5:
                    fuelGrade = "АИ-100";
                    break;
                default:
                    System.out.println("Ошибка ввода");
                    check=true;
            }
        }while (check);
        return fuelGrade;
    }
    private int fuelCapacityCheck() throws IOException {
        int capacity;
        do{
            System.out.println("Введите объём перевозимого топлива(Литров):");

            capacity = Integer.parseInt(reader.readLine());
        }while(capacity<=0);

        return capacity;
    }
    private double routeDurationCheck() {

        boolean check;
        double input=0;
        do {
            check=false;
            try {
                System.out.println("Введите длину маршрута(Км):");

                input=Double.parseDouble(reader.readLine());
                if(input<=0)  throw new NumberFormatException();;

            } catch (NumberFormatException | IOException e) {

                System.out.println("Ошибка ввода. Повторите ввод");
                check=true;
            }
        }while(check);

        return input;
    }
    private String statusCheck() throws IOException {
        boolean check;
        String[] status = new String[]{"Доставлен", "Заказ формируется", "В пути", "Отменён"};
        String out="";
        do {

            System.out.println("Выберите состояние доставки:");
            System.out.println("1-Доставлен");
            System.out.println("2-Заказ формируется");
            System.out.println("3-В пути");
            System.out.println("4-Отменён");
            int input = Integer.parseInt(reader.readLine());
            check= false;
            switch (input) {
                case 1:
                    out= status[0];
                    break;
                case 2:
                    out= status[1];
                    break;
                case 3:
                    out= status[2];
                    break;
                case 4:
                    out= status[3];
                    break;
                default:
                    System.out.println("Ошибка ввода");
                    check= true;


            }
        }while (check);
        return out;
    }
    public void sortByProfit() throws SQLException {

        String sql="SELECT * FROM routes ORDER BY profit DESC;";


        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Происходит сортировка...");
        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");

            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);
            System.out.println("\n=========================\n");

        }


    }
    public void sortByRouteDuration() throws SQLException {

        String sql="SELECT * FROM routes ORDER BY routeDuration DESC;";


        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Происходит сортировка...");
        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");

            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);
            System.out.println("\n=========================\n");

        }


    }
    public void sortByStatus() throws SQLException {

        String sql="SELECT * FROM routes ORDER BY status;";


        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Происходит сортировка...");
        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");

            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);
            System.out.println("\n=========================\n");

        }


    }
    public void sortByTruck() throws SQLException {

        String sql="SELECT * FROM routes ORDER BY truck_id;";

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        Statement statement2 = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Происходит сортировка...");
        while (resultSet.next()) {
            int truck_id=resultSet.getInt("truck_id");
            int id = resultSet.getInt("id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");

            String sql2="SELECT * FROM trucks WHERE id = "+"\'"+truck_id+"\'";
            ResultSet resultSet2 = statement2.executeQuery(sql2);
            resultSet2.first();
            String carName = resultSet2.getString("carName");
            String carPlateNumber = resultSet2.getString("carPlateNum");
            int yearOfCar=resultSet2.getInt("yearOfCar");


            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);
            System.out.println("Название автомобиля: " + carName);
            System.out.println("Номерной знак:" + carPlateNumber);
            System.out.println("Год выпуска авто: " + yearOfCar);
            System.out.println("\n=========================\n");

        }


    }
    public void sortByCapacity() throws SQLException {

        String sql="SELECT * FROM routes ORDER BY capacity;";


        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Происходит сортировка...");
        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            fuelGrade = resultSet.getString("fuelGrade");
            status = resultSet.getString("status");
            profit = resultSet.getDouble("profit");
            routeDuration = resultSet.getDouble("routeDuration");
            capacity=resultSet.getInt("capacity");

            System.out.println("id: " + id);
            System.out.println("Тип топлива: " + fuelGrade);
            System.out.println("Объём топлива: " + capacity);
            System.out.println("Длина маршрута:" + routeDuration);
            System.out.println("Статус перевозки: " + status);
            System.out.println("Сумма перевозки: " + profit);
            System.out.println("\n=========================\n");

        }


    }
    public void deleteRoute() throws SQLException, IOException {

        showRoutes();
        System.out.println("Выберете данные  для удаления(id):");
        String deletion= reader.readLine();
        String sql="DELETE FROM routes WHERE id = "+"\'"+deletion+"\'";

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
         statement.executeUpdate(sql);
        System.out.println("Удаляем запись...");
    }


}