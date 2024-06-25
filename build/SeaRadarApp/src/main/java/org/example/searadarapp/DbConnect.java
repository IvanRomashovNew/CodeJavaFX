package org.example.searadarapp;

import java.sql.*;

public class DbConnect {
    public Connection ConnectionToDb() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SeaRadarDB", "postgresnew", "111");
            if (connection != null) {
                System.out.println("Подключён к БД");
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet tables = dbm.getTables(null, null, "Sea_Messages", null);
                if (tables.next()) {
                    System.out.println("Таблицы в базе данных существуют");
                }
                else {
                    System.out.println("Таблиц в базе данных не существует, начинаем создание таблиц...");
                    Statement stmt = connection.createStatement();
                    String sql = "CREATE TABLE public.Types_Of_Messages(" +
                            "id_type serial PRIMARY KEY," +
                            "type_of_message varchar(3)" +
                            ");" +
                            "CREATE TABLE public.Types_Of_Protocols(" +
                            "id_type serial PRIMARY KEY," +
                            "type_of_protocol varchar(10)" +
                            ");" +
                            "CREATE TABLE public.Sea_Messages(" +
                            "id_message serial PRIMARY KEY," +
                            "input_message varchar," +
                            "output_message varchar," +
                            "type_of_protocol int," +
                            "FOREIGN KEY (type_of_protocol) REFERENCES public.Types_Of_Protocols(id_type)," +
                            "type_of_message int," +
                            "FOREIGN KEY (type_of_message) REFERENCES public.Types_Of_Messages(id_type)" +
                            ");" +
                            "INSERT INTO public.Types_Of_Messages(type_of_message) VALUES ('ТТМ'),('VHW'),('RSD');" +
                            "INSERT INTO public.Types_Of_Protocols(type_of_protocol) VALUES ('МР-231'),('МР-231-3');";
                    stmt.executeUpdate(sql);
                    System.out.println("Таблицы созданы");
                }
            } else {
                System.out.println("Не удалось подключиться к БД");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }
    public void AddNewMessage(Connection connection,String messageInput, String messageOutput,int typeProtocol,int typeOfMessage){
        Statement statement;
        try{
            String query = "INSERT INTO Sea_Messages(input_message,output_message,type_of_protocol,type_of_message) VALUES('"+messageInput+"','"+messageOutput+"','"+typeProtocol+"',"+typeOfMessage+")";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Сообщение добавлено");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public Message LastMessageViewMr_231(Connection connection) {
        Statement statement;
        Message newMessage = null;
        try {
            String query = "SELECT sm.id_message,sm.input_message,sm.output_message,top.type_of_protocol,tom.type_of_message " +
                    "FROM public.sea_messages sm " +
                    "JOIN public.types_of_protocols top ON sm.type_of_protocol = top.id_type " +
                    "JOIN public.types_of_messages tom ON sm.type_of_message = tom.id_type " +
                    "WHERE sm.type_of_protocol = 1 " +
                    "ORDER BY sm.id_message DESC " +
                    "LIMIT 1;";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int id_message = resultSet.getInt("id_message");
                String input_message = resultSet.getString("input_message");
                String output_message = resultSet.getString("output_message");
                String type_of_protocol = resultSet.getString("type_of_protocol");
                String type_of_message = resultSet.getString("type_of_message");

                // Создаем объект Message с полученными данными
                newMessage = new Message(input_message, id_message, output_message, type_of_protocol, type_of_message);
            }

            System.out.println("Последнее сообщение по протоколу МР-231 получено");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return newMessage;
    }
    public Message LastMessageViewMr_231_3(Connection connection) {
        Statement statement;
        Message newMessage = null;
        try {
            String query = "SELECT sm.id_message,sm.input_message,sm.output_message,top.type_of_protocol,tom.type_of_message " +
                    "FROM public.sea_messages sm " +
                    "JOIN public.types_of_protocols top ON sm.type_of_protocol = top.id_type " +
                    "JOIN public.types_of_messages tom ON sm.type_of_message = tom.id_type " +
                    "WHERE sm.type_of_protocol = 2 " +
                    "ORDER BY sm.id_message DESC " +
                    "LIMIT 1;";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int id_message = resultSet.getInt("id_message");
                String input_message = resultSet.getString("input_message");
                String output_message = resultSet.getString("output_message");
                String type_of_protocol = resultSet.getString("type_of_protocol");
                String type_of_message = resultSet.getString("type_of_message");

                // Создаем объект Message с полученными данными
                newMessage = new Message(input_message, id_message, output_message, type_of_protocol, type_of_message);
            }

            System.out.println("Последнее сообщение по протоколу МР-231-3 получено");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return newMessage;
    }
}

