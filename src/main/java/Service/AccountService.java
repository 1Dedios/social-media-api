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











}