package pl.pjatk.banks.model;

public class TransactionResult {

    //Kiedy wartosc updated balance jest <0 , transakcja nie powiodła sie
    //Zakladamy, ze w naszym banku nie ma mozliwosci miec salda ujemnego
    private double updatedBalance;
    private TransactionStatus transactionStatus;

    public TransactionResult(double updatedBalance, TransactionStatus transactionStatus) {
        this.updatedBalance = updatedBalance;
        this.transactionStatus = transactionStatus;
    }

    public double getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(double updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
