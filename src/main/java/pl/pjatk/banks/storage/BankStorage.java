package pl.pjatk.banks.storage;

import org.springframework.stereotype.Repository;
import pl.pjatk.banks.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BankStorage {
    private final List<Account> accounts = new ArrayList<>();

    public void registerAccount(Account account) {
        accounts.add(account);
    }

    public Optional<Account> findById(long id) {
        return accounts.stream()
                .filter(account -> account.getId() == id)
                .findFirst();
    }
}
