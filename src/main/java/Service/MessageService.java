package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;


public class MessageService {

    MessageDAO messageDAO;


    public MessageService() {
        messageDAO = new MessageDAO();
    }


    public MessageService (MessageDAO messageDAO) {

        this.messageDAO = messageDAO;
    }

    public static Message newMessage(Message message) {
        Message newMessage = MessageDAO.insertNewMessage(message);
        return newMessage;
    }

    public static List<Message> getAllMessages () {

        if(MessageDAO.getAllMessages() == null) {
            return null;
        }

        return MessageDAO.getAllMessages();

    }

    public static Message getMessageById (int id) {

        return MessageDAO.getMessageById(id);




    }

    public static Message deletMessageById (int id) {

        if (MessageDAO.getMessageById(id) == null) {
            return null;
        }
        return MessageDAO.getMessageById(id);





    }


    public static Message updateMessageById (int message_id, String message) {

        if (MessageDAO.getMessageById(message_id) == null) {
            return null;
        }
        return MessageDAO.getMessageById(message_id);


    }




    public static List<Message> getAllMessagesByAccountId (int id) {

        if(MessageDAO.getAllMessagesByAccountId(id) == null) {
            return null;
        }
        return MessageDAO.getAllMessagesByAccountId(id);

        
    }












}