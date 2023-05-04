package Service;

import DAO.AccountDAO;
import Model.Account;





public class AccountService {

    AccountDAO accountDAO;




    public AccountService () {
        accountDAO = new AccountDAO();

    }


    public AccountService (AccountDAO accountDAO) {
        this.accountDAO = accountDAO;

    }













    public static Account addUser (Account account) {
        Account newAccount = AccountDAO.insertUser(account);


        return newAccount; 
    }




    public static String getUserName (String account_username) {
        String currentUser = AccountDAO.getUserName(account_username);
        return currentUser;

    }



    public static int getAccount_Id (int id) {

        int account_id = AccountDAO.getAccountIdByMessagePostedId(id);

        return account_id;



    }











}