package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public static List<Message> getAllMessages () {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Message allMessages = new Message (rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));

                messages.add(allMessages);

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }
        return messages;





    }


    public static Message getMessageById (int id) {


        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT message_id FROM message WHERE message_id=?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Message getMessagesById = new Message (rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                return getMessagesById;
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }
        return null;






    }















}