package DAO;

import java.sql.*;
import Model.Message;
import Util.ConnectionUtil;




public class MessageDAO {


    public static Message insertNewMessage (Message message) {

        // Connection connect = ConnectionUtil.getConnection();

        /* place these within a try catch block */

        // String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

        // PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // preparedStatement.setInt(1, message.posted_by);
        // preparedStatement.setString(2, message.message_text);
        // preparedStatement.setBigInt(3, message.time_posted_epoch);

        // ResultSet rs = preparedStatement.executeUpdate();

        // while (rs.next())
        // Message newMessage = new Message (rs.getInt("posted_by"), rs.getString("message_text"), rs.getBigInt("time_posted_epoch"));
        // return new Message;

        Connection connect = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.posted_by);
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message.time_posted_epoch);

            preparedStatement.executeUpdate();

            ResultSet pKeyResultSet = preparedStatement.getGeneratedKeys();

            if (pKeyResultSet.next()) {
                int generated_message_id = pKeyResultSet.getInt(1);
                return new Message (generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;



    } 















}