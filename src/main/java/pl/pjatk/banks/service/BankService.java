package pl.pjatk.banks.service;

import org.springframework.stereotype.Service;
import pl.pjatk.banks.model.Account;
import pl.pjatk.banks.model.TransactionResult;
import pl.pjatk.banks.model.TransactionStatus;
import pl.pjatk.banks.storage.BankStorage;

import java.util.Optional;

@Service
public class BankService {

    private final BankStorage bankStorage;


    public BankService(BankStorage bankStorage) {
        this.bankStorage = bankStorage;
    }

    public Account registerAccount(double balance, String name) {
        if (balance < 0) {
            return null;
        }
        Account account = new Account(balance, name);
        bankStorage.registerAccount(account);
        return account;
    }

    public TransactionResult createTransfer(long id, double transferValue) {
        if (transferValue <= 0) {
            System.out.println("Invalid transfer value");
            return new TransactionResult(-1, TransactionStatus.INVALID_TRANSFER_VALUE);
        }

        Optional<Account> accountOptional = bankStorage.findById(id);

        if (accountOptional.isEmpty()) {
            System.out.println("Invalid account id");
            return new TransactionResult(-1, TransactionStatus.INVALID_ACCOUNT_DATA);
        }

        Account account = accountOptional.get();
        if (account.getBalance() >= transferValue) {
            account.setBalance(account.getBalance() - transferValue);
            return new TransactionResult(account.getBalance(), TransactionStatus.ACCEPTED);
        }

        System.out.println("You don't have enough founds");
        return new TransactionResult(-1, TransactionStatus.DECLINED);
    }

    public TransactionResult depositMoney(long id, double transferValue) {
        if (transferValue <= 0) {
            System.out.println("Invalid transfer value");
            return new TransactionResult(-1, TransactionStatus.INVALID_TRANSFER_VALUE);
        }

        Optional<Account> accountOptional = bankStorage.findById(id);

        if (accountOptional.isEmpty()) {
            System.out.println("Invalid account id");
            return new TransactionResult(-1, TransactionStatus.INVALID_ACCOUNT_DATA);
        }

        Account account = accountOptional.get();
        account.setBalance(account.getBalance() + transferValue);
        return new TransactionResult(account.getBalance(), TransactionStatus.ACCEPTED);
    }

    public Account getAccountData(long id) {
        Optional<Account> accountOptional = bankStorage.findById(id);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            System.out.println(account);
            return account;
        }

        System.out.println("Invalid account id");
        return null;
    }
}
