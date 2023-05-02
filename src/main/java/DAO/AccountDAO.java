package DAO;



import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;



public class AccountDAO {

    // create method to check for duplicate usernames in the database


    public Account checkDuplicateUserName(String str) throws SQLException {

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

        // while (rs.next())
        // create a new instance of account to return - when returning an account from db you need all three constructors
        // Account existingAccount = a username already in the database;
        // return existingAccount;

        while (rs.next()) {
            Account existingAccount = new Account (Account.getAccount_id(), rs.getUsername(), Account.getPassword());

            return existingAccount;
        }




    }



















}