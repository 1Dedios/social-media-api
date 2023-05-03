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

        Account newAccount = AccountDAO.insertUser(accountDAO);
        return newAccount; 
    }











}