import database.Database;
import dictionary.TransactionType;
import entity.Account;
import entity.Customer;
import entity.Transaction;
import org.apache.ibatis.jdbc.ScriptRunner;
import repository.*;
import service.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class MainApp {
    private static Customer loggedCustomer = null;
    private static Account loggedAccount = null;
    private static final AccountService accountService = new AccountService(new AccountRepository());
    private static final CustomerService customerService = new CustomerService(new CustomerRepository());
    private static final TransactionService transactionService = new TransactionService(new TransactionRepository());
    private static final BankService bankService = new BankService(new ViewRepository());
    private static final AddressService addressService = new AddressService(new AddressRepository());


    public static void main(String[] args) {
        //TODO Comment this two methods after first run
        createDatabaseTables();
        insertData();
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the bank system");
        while (loggedAccount == null) {
            System.out.print("Login to get access: (default data Login: Admin, Password: Admin)\nLogin:");
            final String login = scanner.nextLine();

            System.out.print("Password: ");
            final String password = scanner.nextLine();

            loggedAccount = accountService.login(login, password);

            if (loggedAccount == null) {
                System.out.println("Incorrect login or password. Try again");
                continue;
            }
            loggedCustomer = customerService.getCustomer(loggedAccount.getCustomer());
        }
        boolean continueWhile = true;
        while (continueWhile) {
            printMenu();

            int option;
            try {
                option = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid data format");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("Your currently balance is: " + loggedAccount.getBalance());
                    break;
                case 2:
                    System.out.println("Your accounts balance sum is: "
                            + accountService.findBalanceFromAllAccountsByCustomerId(loggedCustomer.getId()));
                    break;
                case 3:
                    System.out.println("______You are transferring money______");
                    System.out.println("Your balance is: " + loggedAccount.getBalance());

                    System.out.println("Please write your bank's account number to send money");
                    String bankaAcountNumber = new Scanner(System.in).nextLine();
                    final Account toAccount = accountService.findByAccountNumber(bankaAcountNumber);
                    if (toAccount == null) {
                        System.out.println("Account does not exist");
                        continue;
                    }
                    System.out.println("How much money do you want to send?");
                    String moneyToSend = new Scanner(System.in).nextLine();
                    final BigDecimal valueMoneyToSend = new BigDecimal(moneyToSend);
                    if (loggedAccount.getBalance().compareTo(valueMoneyToSend) < 0) {
                        System.out.println("Your balance is not enough to send the provide amount of money!");
                        continue;
                    }


                    loggedAccount.setBalance(loggedAccount.getBalance().subtract(valueMoneyToSend));
                    toAccount.setBalance(toAccount.getBalance().add(valueMoneyToSend));
                    accountService.updateBalance(loggedAccount);
                    accountService.updateBalance(toAccount);

                    List<Transaction> transactions = Transaction.create(TransactionType.DEBIT, valueMoneyToSend, loggedAccount.getId(), toAccount.getId());
                    for (Transaction transaction : transactions) {
                        transactionService.saveTransaction(transaction);
                    }
                    System.out.println("______Money is transferred_______");
                    break;
                case 4:
                    System.out.println("You need to put your data. Follow instructions.");
                    System.out.println("Put your login: ");
                    final String login = new Scanner(System.in).nextLine();

                    if (login == null || login.isBlank() || login.trim().length() < 5 || login.trim().length() > 25) {
                        System.out.println("Wrong login. You have to use min 5 characters to max 25 characters");
                        continue;
                    }

                    System.out.println("Provide  your password: ");
                    final String password = new Scanner(System.in).nextLine();

                    if (password == null || password.isBlank() || password.trim().length() < 5 || password.trim().length() > 25) {
                        System.out.println("Wrong password. You have to use min 5 characters to max 25 characters");
                        continue;
                    }

                    final Account account = new Account(loggedCustomer.getId(), generateBankAccountNumber(), new BigDecimal(0), login, password, false);
                    accountService.saveAccount(account);

                    System.out.println("Your new account is created!");

                    break;
                case 5:
                    System.out.println("WARNING! If you delete currently account we log you out!");
                    final List<Account> customerAccounts = accountService.getCustomerAccounts(loggedCustomer.getId());
                    for (int i = 0; i < customerAccounts.size(); i++) {
                        System.out.println((i + 1) + ". " + customerAccounts.get(i));
                    }
                    System.out.println("Which account do you want to delete? Check from the provided list");
                    int chosedAccountToDelete;
                    try {
                        chosedAccountToDelete = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid data format");
                        continue;
                    }

                    if (chosedAccountToDelete > customerAccounts.size() + 1 || chosedAccountToDelete < 1) {
                        System.out.println("WARNING! Wrong account number");
                        continue;
                    }

                    if (customerAccounts.get(chosedAccountToDelete - 1).getId().equals(loggedAccount.getId())) {
                        accountService.deleteAccount(loggedAccount.getId());
                        loggedAccount = null;
                        loggedCustomer = null;
                        continueWhile = false;
                        System.out.println("_______Account with number " + chosedAccountToDelete + " is deleted!_________");
                        System.out.println("**You are logged out**");
                        continue;
                    }

                    accountService.deleteAccount(customerAccounts.get(chosedAccountToDelete - 1).getId());
                    System.out.println("________Account with number " + chosedAccountToDelete + " is deleted!________");
                    break;
                case 6:
                    System.out.println("Do you want delete all your bank's accounts? Write Y or N");
                    String response = new Scanner(System.in).nextLine().toLowerCase();
                    if (response.equals("y")) {
                        customerService.deleteEveryCustomerAccount(loggedCustomer.getId());
                        loggedAccount = null;
                        loggedCustomer = null;
                        continueWhile = false;
                        System.out.println("**You are logged out**");

                        continue;
                    } else{
                        System.out.println("Your accounts are not deleted");
                    }
                    break;
                case 7:
                    System.out.print(loggedCustomer);
                    System.out.println(addressService.getAddress(loggedCustomer.getAddress()));
                    break;
                case 8:
                    System.out.println(accountService.getCustomerAccounts(loggedCustomer.getId()));
                    break;
                case 9:
                    transactionService.getAccountTransactions(loggedAccount.getId()).forEach(System.out::println);
                    break;
                case 10:
                    continueWhile = false;
                    break;
                case 11:
                    if (loggedAccount.getAdmin()) {
                        System.out.println(bankService.getBankCapitalInfo());
                    }
                    break;
                case 12:
                    if (loggedAccount.getAdmin()) {
                        List<Customer> debtors = customerService.findDebtors();
                        if (debtors.size() < 1) {
                            System.out.println("In our bank we do not have debtors!");
                        } else {
                            debtors.forEach(System.out::println);
                        }
                        break;
                    }
                default:
                    System.out.println("This option is not available");
            }
        }

        System.out.println("Goodbye!");
    }

    private static String generateBankAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(ThreadLocalRandom.current().nextInt(0, 9 + 1));
        }
        return stringBuilder.toString();
    }

    private static void printMenu() {
        if (loggedAccount.getAdmin()) {
            System.out.println("Choose option from 1 to 12");
        } else {
            System.out.println("Choose option from 1 to 11");
        }
        System.out.println("******************************");
        System.out.println("Logged as: " + loggedCustomer.getName() + " " + loggedCustomer.getSurname());
        System.out.println("1. Check account's balance");
        System.out.println("2. Check all accounts sum balance");
        System.out.println("3. Transfer money");
        System.out.println("4. Create new account");
        System.out.println("5. Delete account");
        System.out.println("6. Delete all your accounts");
        System.out.println("7. Check personal data");
        System.out.println("8. Print information from all accounts");
        System.out.println("9. Print all transactions");
        System.out.println("10. Exit");
        if (loggedAccount.getAdmin()) {
            System.out.println("11. Print bank status");
            System.out.println("12. Print all debtors");
        }
        System.out.println("******************************");

    }


    public static void createDatabaseTables() {
        final Connection connection = Database.getConnection();
        InputStreamReader schemaTableReader = null;

        try {
            System.out.println("\n**************************************CREATE TABLES**************************************");

            ScriptRunner runner = new ScriptRunner(connection);
            schemaTableReader = new InputStreamReader(new FileInputStream("src/main/resources/create-tables.sql"));
            runner.runScript(schemaTableReader);
            schemaTableReader.close();

            connection.commit();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertData() {
        final Connection connection = Database.getConnection();
        InputStreamReader dataInitReader = null;

        try {
            System.out.println("\n**************************************INSERT DATA**************************************");

            ScriptRunner runner = new ScriptRunner(connection);
            dataInitReader = new InputStreamReader(new FileInputStream("src/main/resources/init-data.sql"));
            runner.runScript(dataInitReader);
            dataInitReader.close();
            Thread.sleep(2000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
