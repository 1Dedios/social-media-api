package DAO;



import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;



public class AccountDAO {

    // create method to check for duplicate usernames in the database


    public static boolean checkDuplicateUserName(String str) {

        try{
            // create connection to database
            Connection connection = ConnectionUtil.getConnection();

            // sql statement
            String sql = "SELECT * FROM account WHERE username=?";

            // precompiled sql statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // sets username to value of str argument
            preparedStatement.setString(2, str);

            // executes the query
            ResultSet rs = preparedStatement.executeQuery();

            // ResultSet Interface - next() moves to another row from its current position
            // checking if username in db matches given str - existingAccount.equals(str);
            // if it does return true - else false;

            while (rs.next()) {
            
                Account existingAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            
                return existingAccount.equals(str);
            
            }


            
        } catch(SQLException e) {
            System.out.println(e.getMessage());

        }
        return false; 

        



    }



















}