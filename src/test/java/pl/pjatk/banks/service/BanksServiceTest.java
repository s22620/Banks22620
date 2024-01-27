package pl.pjatk.banks.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pjatk.banks.model.Account;
import pl.pjatk.banks.storage.BankStorage;

public class BanksServiceTest {
    private BankService bankService;

    @BeforeEach
    void setUp() {
        BankStorage bankStorage = new BankStorage();
        bankService = new BankService(bankStorage);
    }

    @Test
    void shouldCorrectRegisterAccount() {
        //when
        double balance = 100;
        String name = "Jan Kowalski";
        Account account = bankService.registerAccount(balance, name);
        //then
        Assertions.assertNotNull(account);
        Account result = bankService.getAccountData(account.getId());
        Assertions.assertEquals(account.getBalance(), result.getBalance());
        Assertions.assertEquals(account.getOwnerName(), result.getOwnerName());
    }

    @Test
    void shouldntCreateAccountBecauseBalanceIsIncorrect() {
        //when
        double balance = -100;
        String name = "Jan Kowalski";
        Account account = bankService.registerAccount(balance, name);
        //then
        Assertions.assertNull(account);
    }
}
