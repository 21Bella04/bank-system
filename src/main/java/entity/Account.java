package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Account {

    private Integer id;
//    private Customer customer;
    private Integer customer;
    private String bankAccountNumber;
    private BigDecimal balance;
    private String login;
    private String password;
    private Boolean admin;

    public Account(Integer id, Integer customer, String bankAccountNumber, BigDecimal balance, String login, String password) {
        if (id == null)
            throw new IllegalArgumentException("Id can't be null");

        if (customer == null)
            throw new IllegalArgumentException("customer can't be null");

        if (bankAccountNumber == null || bankAccountNumber.isBlank() || bankAccountNumber.length() > 28)
            throw new IllegalArgumentException("bankAccountNumber can't be null");

        if (balance == null)
            throw new IllegalArgumentException("balance can't be null");

        if (login == null || login.isBlank() || login.length() > 255)
            throw new IllegalArgumentException("login can't be null");

        if (password == null || password.isBlank() || password.length() > 255)
            throw new IllegalArgumentException("password can't be null");

        this.id = id;
        this.customer = customer;
        this.bankAccountNumber = bankAccountNumber;
        this.balance = balance;
        this.login = login;
        this.password = password;
    }

    public Account(Integer customer, String bankAccountNumber, BigDecimal balance, String login, String password, boolean admin) {

        if (customer == null)
            throw new IllegalArgumentException("customer can't be null");

        if (bankAccountNumber == null || bankAccountNumber.isBlank() || bankAccountNumber.length() > 28)
            throw new IllegalArgumentException("bankAccountNumber can't be null");

        if (balance == null)
            throw new IllegalArgumentException("balance can't be null");

        if (login == null || login.isBlank() || login.length() > 255)
            throw new IllegalArgumentException("login can't be null");

        if (password == null || password.isBlank() || password.length() > 255)
            throw new IllegalArgumentException("password can't be null");

        this.id = id;
        this.customer = customer;
        this.bankAccountNumber = bankAccountNumber;
        this.balance = balance;
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Accounts: " +
                " BankAccountNumber='" + bankAccountNumber +
                " Balance=" + balance + "\n";
    }
}
