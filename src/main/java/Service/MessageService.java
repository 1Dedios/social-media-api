package Service;

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









}