package pl.pjatk.banks.model;

public class Account {

    //Id generowane automatycznie dla kazdego konta
    //W Przypadku ID podczas wyszukiwania dajemy mozliwosc podanai liczby ujemnej
    private long id;
    private double balance;
    private String ownerName;

    private static long idCounter = 0;

    public Account(double balance,String ownerName) {
        this.balance = balance;
        this.ownerName = ownerName;
        id = idCounter++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
