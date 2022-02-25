package entity;

import dictionary.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Transaction {
    private Integer id;
    private LocalDateTime date;
    private TransactionType type;
    private BigDecimal amount;
    private Integer account;

    public Transaction(TransactionType type, BigDecimal amount, Integer account) {
        this.type = type;
        this.amount = amount;
        this.account = account;
    }

    public static List<Transaction> create(TransactionType type, BigDecimal amount, Integer account, Integer toAccount) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(type, amount, account));
        if (TransactionType.DEBIT == type) {
            transactions.add(new Transaction(TransactionType.SUPPLY, amount, toAccount));
        } else if (TransactionType.SUPPLY == type) {
            transactions.add(new Transaction(TransactionType.DEBIT, amount, toAccount));
        } else {
            throw new IllegalStateException("Illegal transaction type");
        }
        return transactions;
    }

    @Override
    public String toString() {
        return "Transactions: " +
                "\n Date: " + date +
                "\n Type: " + type +
                "\n Amount: " + amount + "\n";
    }
}
