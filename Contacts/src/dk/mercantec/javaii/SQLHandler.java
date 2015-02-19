package dk.mercantec.javaii;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-02-2015.
 */
public class SQLHandler {
    public SQLHandler(){
        setupDBIfNeeded();
    }



    boolean insertInternal(Internal internal){
        try {
            Connection connection = fetchConnection();
            String sql = "INSERT INTO internal (name, phone, email, department) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, internal.getName());
            preparedStatement.setString(2, internal.getPhone());
            preparedStatement.setString(3, internal.getEmail());
            preparedStatement.setString(4, internal.getDepartment());

            boolean success = preparedStatement.execute();
            if(!success){
                System.out.println("Something went wrong when inserting an internal user to the database");
            }
            connection.close();

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean insertExternal(External external){
        try {
            Connection connection = fetchConnection();
            String sql = "INSERT INTO external (name, phone, email, company) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, external.getName());
            preparedStatement.setString(2, external.getPhone());
            preparedStatement.setString(3, external.getEmail());
            preparedStatement.setString(4, external.getCompany());

            boolean success = preparedStatement.execute();
            if(!success){
                System.out.println("Something went wrong when inserting an internal user to the database");
            }
            connection.close();

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    List<Internal> loadInternal(){
        List<Internal> internals = new ArrayList<Internal>();
        try {
            Connection connection = fetchConnection();
            String sql = "SELECT name, phone, email, department FROM internal";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Internal I = new Internal(
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("department")
                );
                internals.add(I);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internals;
    }
    List<External> loadExternal(){
        List<External> externals = new ArrayList<External>();
        try {
            Connection connection = fetchConnection();
            String sql = "SELECT name, phone, email, company FROM external";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                External E = new External(
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("company")
                );
                externals.add(E);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return externals;
    }


    private void setupDBIfNeeded(){

        try
        {
            Connection connection = fetchConnection();
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS internal (id integer, name string, phone string, email string, department string)");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS external (id integer, name string, phone string, email string, company string)");
            preparedStatement.executeUpdate();

            connection.close();

        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    private Connection fetchConnection(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:contact.db");
            return connection;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
