package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

// my imports

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
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

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
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
        if (account.getUsername() ==  "" || getPassword.length() < 4 || account.getUsername() == checkDuplicateUserName(account.getUsername())) {

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


}





/* ## 1: Our API should be able to process new User registrations.

As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will 
not contain an account_id.

- The registration will be successful if and only if the username is not blank, the password is at least 4 
characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a 
JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful, the response status should be 400. (Client error) */