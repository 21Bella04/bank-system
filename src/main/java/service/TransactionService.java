package service;

import entity.Transaction;
import lombok.AllArgsConstructor;
import repository.TransactionRepository;

import java.util.List;

@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction getTransaction(int id) {
        return transactionRepository.getTransaction(id);
    }

    public List<Transaction> getAccountTransactions(int accountId) {
        return transactionRepository.getAccountTransactions(accountId);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public void deleteCustomer(Integer transactionId) {
        transactionRepository.deleteTransaction(transactionId);
    }
}
