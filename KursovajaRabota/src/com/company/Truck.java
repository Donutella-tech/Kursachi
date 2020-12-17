package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Truck  {
    BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
     private String carName;
     private String carPlateNumber;
     private int yearOfCar;
    Connection connection = Main.getConnection();

    public Truck() throws IOException, SQLException {}
    public void fullFillTruck() throws SQLException, IOException {


     carName=carCheck();
      yearOfCar=yearOfCarCheck();
      carPlateNumber=carPlateNumberCheck();
      Connection connection = Main.getConnection();
      Statement statement = connection.createStatement();
      String input="INSERT trucks(carName,carPlateNum,yearOfCar,delete_check) VALUES ("+"\'" + carName+"\',\'"+carPlateNumber+"\',\'"+yearOfCar+"\',0)";
      statement.executeUpdate(input);

    }
    public void showTrucks() throws SQLException {
        String sql="SELECT * FROM trucks";
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
            carName = resultSet.getString("carName");
            carPlateNumber = resultSet.getString("carPlateNum");
            yearOfCar = resultSet.getInt("yearOfCar");
            String sql2 = "SELECT * FROM drivers WHERE   truck_id = " + "\'" + id + "\'";


            System.out.println("id: " + id);
            System.out.println("Название автомобиля: " + carName);
            System.out.println("Год выпуска авто: " + yearOfCar);
            System.out.println("Номерной знак:" + carPlateNumber);

                ResultSet resultSet2 = statement2.executeQuery(sql2);
                resultSet2.first();
                int delete_check=resultSet2.getInt("delete_check");
                if(delete_check!=1) {
                    String driverName = resultSet2.getString("driverName");
                    int yearOfBirth = resultSet2.getInt("yearOfBirth");
                    System.out.println("ФИО водителя:" + driverName);
                    System.out.println("Год рождения водителя:" + yearOfBirth);
                    int count = 1;
                    while (resultSet2.next()) {
                        driverName = resultSet2.getString("driverName");
                        System.out.println("ФИО водителя:" + driverName);
                        yearOfBirth = resultSet2.getInt("yearOfBirth");
                        System.out.println("Год рождения водителя:" + yearOfBirth);
                        count++;
                    }
                    System.out.println();
                    System.out.println("Количество водителей:" + count);
                }
                else {
                    System.out.println("У данного автомобиля нет водителя!");
                }
            System.out.println("\n=========================\n");
        }





        }


    public void searchTruck() throws SQLException, IOException {

            System.out.println("Введите номерной знак: ");

            String search= reader.readLine();

            String sql="SELECT * FROM trucks WHERE carPlateNum LIKE \"%"+search+"%\"";

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
                carName = resultSet.getString("carName");
                carPlateNumber = resultSet.getString("carPlateNum");
                yearOfCar = resultSet.getInt("yearOfCar");
                String sql2 = "SELECT * FROM drivers WHERE truck_id = " + "\'" + id + "\'";

                ResultSet resultSet2 = statement2.executeQuery(sql2);
                resultSet2.first();


                System.out.println("id: " + id);
                System.out.println("Название автомобиля: " + carName);
                System.out.println("Год выпуска авто: " + yearOfCar);
                System.out.println("Номерной знак:" + carPlateNumber);

                String driverName = resultSet2.getString("driverName");
                int yearOfBirth = resultSet2.getInt("yearOfBirth");
                System.out.println("ФИО водителя:" + driverName);
                System.out.println("Год рождения водителя:" + yearOfBirth);
                int count = 1;
                while (resultSet2.next()) {
                    driverName = resultSet2.getString("driverName");
                    System.out.println("ФИО водителя:" + driverName);
                    yearOfBirth = resultSet2.getInt("yearOfBirth");
                    System.out.println("Год рождения водителя:" + yearOfBirth);
                    count++;
                }
                System.out.println();
                System.out.println("Количество водителей:" + count);

                System.out.println("\n=========================\n");

            }
        }
    public void deleteTruck() throws SQLException, IOException {

        showTrucks();
        System.out.println("Выберете данные  для удаления(id):");
        String deletion= reader.readLine();
        String sql="UPDATE trucks SET delete_check=1 WHERE id = "+"\'"+deletion+"\'";

        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.executeUpdate(sql);
        System.out.println("Удаляем запись...");
    }
    private String carCheck() throws IOException {


        String driverName;
        while (true)
        {
            int i = 0;

            System.out.println("Введите марку авто: ");
            driverName=reader.readLine();
            char a[]=driverName.toCharArray();


            while( i < a.length)
            {
                if ((((a[i] < 'a') || (a[i] > 'z')) && ((a[i] < 'A') || (a[i] > 'Z')) && ((a[i] < 'А') || (a[i] > 'Я')) && ((a[i] < 'а') || (a[i] > 'я'))) && (a[i] != ' '))
                {
                    System.out.println("Использование цифр не допустимо");
                    break;

                }
                i++;
            }

            if (i !=a.length)//если не прошло проверку, то повторный ввод
            {
                continue;
            }


            if (a.length < 2)
            {
                System.out.println("Не достигнута минимальная длина  ");
                continue;
            }

            break;
        }

        return driverName;




    }
    private String carPlateNumberCheck() {
        String carPlate="";

        boolean check;

        do {
            check=false;
            try {
                System.out.println("Введите номерной знак автомобиля:");
                carPlate = reader.readLine();
                if (carPlate.length() != 8) throw new NumberFormatException("Неверная длина номера");
                char ch[] = carPlate.toCharArray();
                for (int i = 0; i < ch.length; i++) {
                    if (i == 0 || i == 1){
                        if (ch[i] != 'A' && ch[i] != 'B' && ch[i] != 'E' && ch[i] != 'I' && ch[i] != 'K' && ch[i] != 'M' && ch[i] != 'H' && ch[i] != 'O' && ch[i] != 'P' && ch[i] != 'C' && ch[i] != 'T' && ch[i] != 'X') {
                            throw new NumberFormatException("Вначале номера стоят буквы!");

                        }
                    }
                    if (ch[6] != '-') throw new NumberFormatException("Пропущено ' - ' в номере");

                    if (i > 2 && i < 6 || i == 7) {
                        if (ch[i] < '0' || ch[i] > '9') throw new NumberFormatException("Некорректный формат");

                    }
                }

            } catch (NumberFormatException | IOException e) {

                System.out.println(e);
                check=true;
            }
        }while(check);

        return carPlate ;

    }
    private int yearOfCarCheck() {
        boolean check;
        int input=0;
        do {
            check=false;
            try {
                System.out.println("Введите год выпуска авто:");

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
