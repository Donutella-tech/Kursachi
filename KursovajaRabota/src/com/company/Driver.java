package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driver  {
    private String driverName;
    private int yearOfBirth;
    Connection connection = Main.getConnection();
    BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));



    public Driver() throws IOException, SQLException {


    }
    public void fullFIllDriver() throws SQLException, IOException {
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

         String sql="SELECT * FROM trucks";
        ResultSet resultSet = statement.executeQuery(sql);
        int choice=1;
        System.out.println("1-Выберете автомобиль для водителя");
        System.out.println("2-Добавить новый автомобиль");
        choice=Integer.parseInt(reader.readLine());

        if(choice==1)
        {
            while (resultSet.next()) {

                System.out.println("Получаем запись...");
                int id = resultSet.getInt("id");
                String carName = resultSet.getString("carName");
                String carPlateNum = resultSet.getString("carPlateNum");
                int yearOfCar = resultSet.getInt("yearOfCar");

                System.out.println("id: " + id);
                System.out.println("Название автомобиля: " + carName);
                System.out.println("Номерной знак: " + carPlateNum);
                System.out.println("Год выпуска:" + yearOfCar);
                System.out.println("\n=========================\n");

            }

            System.out.println("Введите необходимый id");
            int truck_id=Integer.parseInt(reader.readLine());
            driverName=driverCheck();
           yearOfBirth=yearOfDriverCheck();
            String input="INSERT drivers(driverName,yearOfBirth,truck_id,delete_check) VALUES ("+"\'" + driverName+"\',\'"+yearOfBirth+"\',\'"+truck_id+"\',0)";
            statement.executeUpdate(input);
        }
        else{
        Truck truck=new Truck();
        truck.fullFillTruck();
        driverName=driverCheck();
        yearOfBirth=yearOfDriverCheck();
            resultSet.last();
            int truck_id = resultSet.getInt("id");
            String input="INSERT drivers(driverName,yearOfBirth,truck_id,delete_check) VALUES ("+"\'" + driverName+"\',\'"+yearOfBirth+"\',\'"+truck_id+"\',0)";
            statement.executeUpdate(input);
        }



    }
    public void showDrivers() throws SQLException {
        String sql="SELECT * FROM drivers";
        Statement statement2 = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Получаем запись...");
        while (resultSet.next()) {


            int id = resultSet.getInt("id");
            driverName = resultSet.getString("driverName");
             yearOfBirth = resultSet.getInt("yearOfBirth");
            int truck_id = resultSet.getInt("truck_id");
            String sql2="SELECT * FROM trucks WHERE id = "+"\'"+truck_id+"\'";
            System.out.println("id: " + id);
            System.out.println("ФИО водителя: " + driverName);
            System.out.println("Год рождения: " + yearOfBirth);

            ResultSet resultSet2 = statement2.executeQuery(sql2);
            resultSet2.first();
            int delete_check=resultSet2.getInt("delete_check");
            if(delete_check!=1) {
                String carName = resultSet2.getString("carName");
                String carPlateNum = resultSet2.getString("carPlateNum");
                int yearOfCar = resultSet2.getInt("yearOfCar");
                System.out.println("Название автомобиля:" + carName);
                System.out.println("Номерной знак:" + carPlateNum);
                System.out.println("Год выпуска авто:"+yearOfCar);

            }
            else {
                System.out.println("У данного водителя нету автомобиля");
            }
            System.out.println("\n=========================\n");

        }




    }
    public void searchDriver() throws SQLException, IOException {
        System.out.println("Введите имя,фамилию или отчество водителя: ");

        String search= reader.readLine();


        String sql="SELECT * FROM drivers WHERE driverName LIKE \"%"+search+"%\"";

        Statement statement2 = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Получаем запись...");
        while (resultSet.next()) {


            int id = resultSet.getInt("id");
            driverName = resultSet.getString("driverName");
            yearOfBirth = resultSet.getInt("yearOfBirth");
            int truck_id = resultSet.getInt("truck_id");
            String sql2="SELECT * FROM trucks WHERE id = "+"\'"+truck_id+"\'";

            ResultSet resultSet2 = statement2.executeQuery(sql2);
            resultSet2.first();
            String carName = resultSet2.getString("carName");
            String carPlateNum = resultSet2.getString("carPlateNum");
            int yearOfCar=resultSet2.getInt("yearOfCar");


            System.out.println("id: " + id);
            System.out.println("ФИО водителя: " + driverName);
            System.out.println("Год рождения: " + yearOfBirth);
            System.out.println("Название автомобиля:" + carName);
            System.out.println("Номерной знак:" + carPlateNum);
            System.out.println("Год выпуска авто:"+yearOfCar);
            System.out.println("\n=========================\n");

        }




    }
    public void deleteDriver() throws SQLException, IOException {

        showDrivers();
        System.out.println("Выберете данные  для удаления(id):");
        String deletion= reader.readLine();
        String sql="UPDATE  drivers SET delete_check=1 WHERE id = "+"\'"+deletion+"\'";


        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.executeUpdate(sql);
        System.out.println("Удаляем запись...");
    }
    private String driverCheck() throws IOException{

        String str;
        boolean check;
        int count_=0;
        do
        {
            check =false;
            System.out.println("Введите Ф.И.О водителя: ");

            str=reader.readLine();


            char[] ch = str.toCharArray();
            for (char c : ch) {
                if(c==' ')count_++;
            }


            if (count_ == 0)
            {

                System.out.println("Вы не указали фамилию и отчество ");
                check = true;
                continue;
            }

            if (count_ == 1)
            {

                System.out.println("Вы не указали отчество ");
                check = true;
                continue;
            }


            for (int i = 0; i < ch.length; i++)
            {
                if ((((ch[i] < 'a') || (ch[i] > 'z')) && ((ch[i] < 'A') || (ch[i] > 'Z')) && ((ch[i] < 'А') || (ch[i] > 'Я')) && ((ch[i] < 'а') || (ch[i] > 'я'))) && (ch[i] != ' '))
                {
                    System.out.println("Использование цифр не допустимо");
                    check = true;
                    break;
                }

            }
            if(check)continue;



        } while (check);

        return str ;
    }
    private int yearOfDriverCheck() {
        boolean check;
        int input=0;
        do {
            check=false;
            try {
                System.out.println("Введите год рождения водителя:");

                String str=reader.readLine();
                if(str.length()!=4)  throw new NumberFormatException();;
                input=Integer.parseInt(str);

            } catch (NumberFormatException | IOException e) {

                System.out.println("Ошибка ввода. Повторите ввод");
                check=true;
            }
        }while(check);

        return input;

    }
}
