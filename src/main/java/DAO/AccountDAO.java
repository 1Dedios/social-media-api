package DAO;



import java.sql.*;

import Model.Account;
// import Model.Account;
import Util.ConnectionUtil;



public class AccountDAO {

    // insertUser method

    public static Account insertUser (Account account) {
        
        // create connection to database
        Connection connection = ConnectionUtil.getConnection();

        try {

    
            // sql statement
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
    
            // precompiled sql statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            // sets values to values of account arg
            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);
    
            // executes the query
            preparedStatement.executeUpdate();

            ResultSet pKeyResultSet = preparedStatement.getGeneratedKeys();



            if(pKeyResultSet.next()) {
                int generated_account_id = (int) pKeyResultSet.getLong(1);

                return new Account (generated_account_id, account.username, account.password);
            }


    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // create method to check for duplicate usernames in the database

    public static Account getUserName (String account_username) {

        // create connection to database
        Connection connection = ConnectionUtil.getConnection();

        try {

            // sql statement
            String sql = "SELECT username FROM account WHERE username=?;";
    
            // precompiled sql statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            // sets username to value of str argument
            preparedStatement.setString(1, account_username);
            
    
            // executes the query
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Account account = new Account (rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));

                return account; 

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;




    } 

    public static int getAccountIdByMessagePostedId (int id) {
        
        Connection connection = ConnectionUtil.getConnection();
        
        try {

            String sql = "SELECT account_id FROM account WHERE account_id=?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Account account = new Account (rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));

                return account.getAccount_id();


            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return 0;
    }
































    
}
