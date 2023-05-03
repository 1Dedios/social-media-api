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


    public Account addUser (Account accountDAO) {

        // TODO: create insertUser method in AccountDAO
        //Account newAccount = accountDAO.insertUser(accountDAO);
        // return newAccount; 
    }











}