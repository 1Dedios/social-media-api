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
        String sql = "SELECT username FROM account WHERE username=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(2, str);

        ResultSet rs = preparedStatement.executeQuery();




    }



















}