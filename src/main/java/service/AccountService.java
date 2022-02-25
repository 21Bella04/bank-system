package service;

import entity.Account;
import lombok.AllArgsConstructor;
import repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getAccount(int id) {
        return accountRepository.getAccount(id);
    }

    public List<Account> getCustomerAccounts(int customerId) {
        return accountRepository.findAccountByCustomerId(customerId);
    }

    public void saveAccount(Account accout) {
        accountRepository.saveAccount(accout);
    }

    public void deleteAccount(Integer accountId) {
        accountRepository.deleteAccount(accountId);
    }

    public Account login(String login, String password) {
        return accountRepository.login(login,password);
    }

    public Account findByAccountNumber(String bankaAcountNumber) {
        return accountRepository.findByAccountNumber(bankaAcountNumber);
    }

    public void updateBalance(Account loggedAccount) {
        accountRepository.updateBalance(loggedAccount);
    }

    public BigDecimal findBalanceFromAllAccountsByCustomerId(Integer customerId){
        return accountRepository.findBalanceFromAllAccountsByUserId(customerId);
    }
}
