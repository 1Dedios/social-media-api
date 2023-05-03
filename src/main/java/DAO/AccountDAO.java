package DAO;



import java.sql.*;

import Model.Account;
// import Model.Account;
import Util.ConnectionUtil;



public class AccountDAO {

    // insertUser method

    public static Account insertUser (Account account) {
        
        try {

            // create connection to database
            Connection connection = ConnectionUtil.getConnection();
    
            // sql statement
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
    
            // precompiled sql statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            // sets values to values of account arg
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            // executes the query
            preparedStatement.executeUpdate();

            ResultSet pKeyResultSet = preparedStatement.getGeneratedKeys();

            if(pKeyResultSet.next()) {
                int generated_account_id = (int) pKeyResultSet.getInt(1);

                return new Account (generated_account_id, account.username, account.password);
            }


    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // create method to check for duplicate usernames in the database

    public static String existingUserName(String str) {

        try {
            // create connection to database
            Connection connection = ConnectionUtil.getConnection();
    
            // sql statement
            String sql = "SELECT username FROM account WHERE username=?;";
    
            // precompiled sql statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            // sets username to value of str argument
            preparedStatement.setString(1, str);
    
            // executes the query
            ResultSet rs = preparedStatement.executeQuery();
    
            // ResultSet Interface - next() moves to another row from its current position
            // checking if username in db matches given str - existingAccount.equals(str);
            // if it does return true - else false;
    
            while (rs.next()) {
            
                String existingAccount = rs.getString("username");
            
                // Account account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"))
                return existingAccount;
                
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
        return null;

    }

    // public static Account getUserLogin () {

    //     try {

    //         // create connection to database
    //         Connection connection = ConnectionUtil.getConnection();
    
    //         // sql statement
    //         String sql = "SELECT account_id, username, password FROM account WHERE username=?";
    
    //         // precompiled sql statement
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
    //         // sets username to value of str argument
    //         preparedStatement.setString(2, str);
    
    //         // executes the query
    //         ResultSet rs = preparedStatement.executeQuery();


    //     } catch () {

    //     }











    } 
