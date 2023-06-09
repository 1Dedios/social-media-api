package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

// my imports

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
// import Model.Message;
import Service.AccountService;
import Service.MessageService;
import DAO.AccountDAO;
import DAO.MessageDAO;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewUserRegistrationHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postNewMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountId);

        return app;
    }





    /* ## 1: Our API should be able to process new User registrations.

As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will 
not contain an account_id.

- The registration will be successful if and only if the username is not blank, the password is at least 4 
characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a 
JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful, the response status should be 400. (Client error) 
*/
    /**
     * 
     *
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws SQLException
     */
    private void postNewUserRegistrationHandler(Context ctx) throws JsonMappingException, JsonProcessingException, SQLException {
        // allows me to read the json data
        ObjectMapper mapper = new ObjectMapper();

        // allows me to read the created fields of a new user account
        Account account = mapper.readValue(ctx.body(), Account.class);

        // CREATE user operation handled by service
        Account addedUser = AccountService.addUser(account);

        // created for readability in if statement
        String getPassword = account.getPassword();

        // reading a login in the database
        Account existingUserName = AccountService.getUserName(account.getUsername());

        System.out.println("Existing user: " + existingUserName); // add this logging statement
        System.out.println("Existing username: " + (existingUserName != null ? existingUserName.getUsername() : "null"));

        // if statements checking for client errors 
        // TODO: method in AccountDAO that compares account.getUsername() with a username in the DB
        if (account.getUsername().isBlank() || getPassword.length() < 4 || existingUserName != null) {

            // if conditions above met then it's a failed registration - ctx.status(400);
            ctx.status(400);
        } else {
            // successful status
            ctx.status(200);
    
            // persistance to the database
            // ctx.json(mapper.writeValueAsString(newAccountCreation));
            ctx.json(mapper.writeValueAsString(addedUser));
    
        }
    }


    /* ## 2: Our API should be able to process User logins.

As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. 
The request body will contain a JSON representation of an Account, not containing an account_id. 
In the future, this action may generate a Session token to allow the user to securely use the site. We will not worry about this for now.

- The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
If successful, the response body should contain a JSON of the account in the response body, including its account_id. 
The response status should be 200 OK, which is the default.
- If the login is not successful, the response status should be 401. (Unauthorized) */

    private void postUserLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {

        // object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        // allows me to read through the provided ctx
        Account account = mapper.readValue(ctx.body(), Account.class);
        
        // READ operation handled by AccountService and queried by DAO - can maybe use this for the duplicate user in user registration
        Account postedUser = AccountService.getUserName(account.getUsername());

        // if username and password match a real account on the db - successful login
        if (account == postedUser) {
            // ctx.status(200);
            ctx.status(200);

            // ctx.json(mapper.writeValueAsString(postedUser))
            ctx.json(postedUser);
        } else {
            ctx.status(401);
        }
        // else not successful
        // ctx.status(401) - unauthorized


    } 


    /* ## 3: Our API should be able to process the creation of new messages.

As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. The request body will contain a JSON representation of a message, 
which should be persisted to the database, but will not contain a message_id.

- The creation of the message will be successful if and only if the message_text is not blank, 
is under 255 characters, and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message, 
including its message_id. The response status should be 200, which is the default. The new message should be persisted to the database.
- If the creation of the message is not successful, the response status should be 400. (Client error)
 */

    private void postNewMessage(Context ctx) throws JsonMappingException, JsonProcessingException {

        // object mapper instance to read message
        ObjectMapper mapper = new ObjectMapper();

        // allows me to read through the provided ctx (message) 
        Message message = mapper.readValue(ctx.body(), Message.class);

        // CREATE operation handled by DAO instantiated with MessageService
        Message newMessage = MessageService.newMessage(message);

        // READ operation handled by AccountDAO and instantiated by AccountService
        int getAccountID = AccountService.getAccount_Id(newMessage.posted_by);


        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255 && message.getPosted_by() == getAccountID) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400); 
        }


    }


/* ## 4: Our API should be able to retrieve all messages.

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

- The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default. 
*/

    private void getAllMessages (Context ctx) {


        
        ctx.status(200);
        ctx.json(MessageService.getAllMessages());


    }

    /* ## 5: Our API should be able to retrieve a message by its ID.

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

- The response body should contain a JSON representation of the message identified by the message_id. 
It is expected for the response body to simply be empty if there is no such message. 
The response status should always be 200, which is the default. 

*/
    private void getMessageByID (Context ctx) throws JsonProcessingException {


        // int provided_id = mapper.readValue(ctx.body(), Integer.TYPE);

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        Message getMessageById = MessageService.getMessageById(message_id);

        // ctx.json(getMessageById);

        if (getMessageById != null) {
            ctx.json(getMessageById);
        } else {
            ctx.result("");

        }






    }

/* ## 6: Our API should be able to delete a message identified by a message ID.

As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

- The deletion of an existing message should remove an existing message from the database. 
If the message existed, the response body should contain the now-deleted message. The response status should be 200, which is the default.
- If the message did not exist, the response status should be 200, but the response body should be empty. 
This is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should respond with the same type of response. 

*/

    private void deleteMessageById (Context ctx) throws JsonProcessingException {


        // using the getMessageById method in MessageService and MessageDAO

        // to parse message_id given in the path parameter
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));


        Message getMessageById = MessageService.getMessageById(message_id);

        // this should equal the variable that retrieved message with a getmessage text method after it
        // String messageText = getMessageById.getMessage_text();

        // ctx.json(mapper.writeValueAsString(messageText));

        if (getMessageById == null) {
            ctx.status(200);
            MessageService.deletMessageById(message_id);
        } else {
            ctx.status(200);
        }





    }


/* ## 7: Our API should be able to update a message text identified by a message ID.

As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. 
The request body should contain a new message_text values to replace the message identified by message_id. 
The request body can not be guaranteed to contain any other information.

- The update of a message should be successful if and only if the message id already exists and the new message_text is not blank and is not over 255 characters. 
If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), 
and the response status should be 200, which is the default. The message existing on the database should have the updated message_text.
- If the update of the message is not successful for any reason, the response status should be 400. (Client error) 

*/

    private void updateMessageById (Context ctx) throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(ctx.body(), Message.class);

        // read value of parameter
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        

        // updating 
        Message updateMessage = MessageService.updateMessageById(message_id, message.message_text);

        // getting message by messageID
        Message getMessageById = MessageService.getMessageById(message_id);

        // update successful if
        if (getMessageById != null && message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
            
            ctx.result(updateMessage.getMessage_text());
        } else {
            ctx.status(400);
        }


    }





/* ## 8: Our API should be able to retrieve all messages written by a particular user.

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.

- The response body should contain a JSON representation of a list containing all messages posted by a particular user, which is retrieved from the database. 
It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default. 

*/


    private void getAllMessagesByAccountId (Context ctx) throws JsonProcessingException {

        // ObjectMapper mapper = new ObjectMapper();

        // Account context_id = mapper.readValue(ctx.body(), Account.class);

        int account_id = Integer.parseInt(ctx.pathParam("account_id"));


        // Message getUserMessages = MessageService.getMessageById(account_id);




        ctx.status(200);
        ctx.json(MessageService.getAllMessagesByAccountId(account_id));




    }










}





