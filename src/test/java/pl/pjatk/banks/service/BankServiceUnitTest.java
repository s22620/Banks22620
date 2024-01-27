package pl.pjatk.banks.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.banks.model.Account;
import pl.pjatk.banks.model.TransactionResult;
import pl.pjatk.banks.model.TransactionStatus;
import pl.pjatk.banks.storage.BankStorage;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BankServiceUnitTest {

    @Mock
    private BankStorage bankStorage;

    @InjectMocks
    private BankService bankService;

    @Test
    void shouldDoTransfer() {
        //given
        Account account = new Account(100, "Jan Kowalski");
        long id = account.getId();
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.of(account));
        //when
        TransactionResult result = bankService.createTransfer(id, 90);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.ACCEPTED);
        Assertions.assertEquals(result.getUpdatedBalance(), 10);
    }

    @Test
    void shouldntDoTransferBecauseAccountWithGivenIdDoesntExists() {
        //given
        long id = 9;
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.empty());
        //when
        TransactionResult result = bankService.createTransfer(id, 90);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.INVALID_ACCOUNT_DATA);
        Assertions.assertEquals(result.getUpdatedBalance(), -1);
    }

    @Test
    void shouldntDoTransferBecauseBalanceIsLowerThanTransferValue() {
        //given
        Account account = new Account(100, "Jan Kowalski");
        long id = account.getId();
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.of(account));
        //when
        TransactionResult result = bankService.createTransfer(id, 110);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.DECLINED);
        Assertions.assertEquals(result.getUpdatedBalance(), -1);
    }

    @Test
    void shouldntDoTransferBecauseTransferValueIsIncorrect() {
        //given
        long id = 9;
        //when
        TransactionResult result = bankService.createTransfer(id, -1);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.INVALID_TRANSFER_VALUE);
        Assertions.assertEquals(result.getUpdatedBalance(), -1);
    }

    @Test
    void shouldCorrectDepositMoney() {
        //given
        Account account = new Account(100, "Jan Kowalski");
        long id = account.getId();
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.of(account));
        //when
        TransactionResult result = bankService.depositMoney(id, 50);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.ACCEPTED);
        Assertions.assertEquals(result.getUpdatedBalance(), 150);
    }

    @Test
    void shouldntDepositMoneyBecauseTransferValueIsIncorrect() {
        //given
        long id = 9;
        //when
        TransactionResult result = bankService.depositMoney(id, -1);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.INVALID_TRANSFER_VALUE);
        Assertions.assertEquals(result.getUpdatedBalance(), -1);
    }

    @Test
    void shouldntDepositMoneyBecauseAccountWithGivenIdDoesntExists() {
        //given
        long id = 9;
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.empty());
        //when
        TransactionResult result = bankService.depositMoney(id, 90);
        //then
        Assertions.assertEquals(result.getTransactionStatus(), TransactionStatus.INVALID_ACCOUNT_DATA);
        Assertions.assertEquals(result.getUpdatedBalance(), -1);
    }

    @Test
    void shouldReturnCorrectAccountData() {
        //given
        Account account = new Account(100, "Jan Kowalski");
        long id = account.getId();
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.of(account));
        //when
        Account result = bankService.getAccountData(id);
        //then
        Assertions.assertEquals(result.getBalance(), account.getBalance());
        Assertions.assertEquals(result.getId(), account.getId());
        Assertions.assertEquals(result.getOwnerName(), account.getOwnerName());
    }

    @Test
    void shouldReturnNullBecauseUserWithGivenIdDoesntExists() {
        //given
        long id = 9;
        Mockito.when(bankStorage.findById(id)).thenReturn(Optional.empty());
        //when
        Account result = bankService.getAccountData(id);
        //then
        Assertions.assertNull(result);
    }
}