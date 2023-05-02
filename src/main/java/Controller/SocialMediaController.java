package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

// my imports

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;

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
        app.post("/register", this::newUserRegistration);
        app.post("/login", this::userLogin);
        app.post("/messages", this::createNewMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);

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
     */
    private void newUserRegistration(Context ctx) throws JsonMappingException, JsonProcessingException {
        // allows me to read the json data
        ObjectMapper mapper = new ObjectMapper();

        // allows me to read the created fields of a new user account
        Account account = mapper.readValue(ctx.body(), Account.class);

        String getPassword = account.getPassword();

        // if statements checking for client errors
        if (account.getUsername() ==  "" || getPassword.length() < 4 || account.getUsername() == AccountDAO.checkDuplicateUserName(account.getUsername())) {

            // if conditions above met then it's a failed registration - ctx.status(400);
            ctx.status(400);
        } else {
            // successful status
            ctx.status(200);

            // if no client errors then we create an account
            Account newAccountCreation = new Account (account.getUsername(), getPassword);
    
            // persistance to the database - EITHER VALUE BELOW WORKS
            // ctx.json(mapper.writeValueAsString(newAccountCreation));
            ctx.json(newAccountCreation.toString());
    
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

    private void userLogin(Context ctx) throws JsonMappingException, JsonProcessingException {

        // object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        // allows me to read the created fields of a new user account
        Account account = mapper.readValue(ctx.body(), Account.class);

        // if username and password match a real account on the db - successful login
        // if (account.username && account.password == "an account you queried in DAO - getAccountByUsernameAndPassword")
        // ctx.status(200);
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

    private void createNewMessage(Context ctx) throws JsonMappingException, JsonProcessingException {

        // object mapper instance to read message
        ObjectMapper mapper = new ObjectMapper();

        

        // successful if
        // message_text != "" || message_text.length() < 255 || posted_by == account_id(posted_by is fkey to account_id - DAO - get account by posted_by )
        // ctx.status(200)
        // ctx.json(message) - including message_id
        // else 
        // ctx.status(400) - client error


    }


/* ## 4: Our API should be able to retrieve all messages.

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

- The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default. 
*/

    private void getAllMessages (Context ctx) throws JsonMappingException, JsonProcessingException {

        // read through request
        ObjectMapper mapper = new ObjectMapper();

        // reading message
        Message message = mapper.readValue(ctx.body(), Message.class);

        // create a getAllMessages method in DAO

        // ctx.status(200)
        // MessageDAO.getAllMessages.toString()


    }

    /* ## 5: Our API should be able to retrieve a message by its ID.

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

- The response body should contain a JSON representation of the message identified by the message_id. 
It is expected for the response body to simply be empty if there is no such message. 
The response status should always be 200, which is the default. 

*/
    private void getMessageByID (Context ctx) throws JsonMappingException, JsonProcessingException {

        // read value of id with Object Mapper

        // read through request
        ObjectMapper mapper = new ObjectMapper();

        // reading message
        Message message = mapper.readValue(ctx.body(), Message.class);


        // ctx.status(200);






    }








}





