package banking;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    private static int counter = 0;

    private int id = counter++;
    private String login;
    private String password;
    private ArrayList<CreditCard> creditCards;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
        this.creditCards = new ArrayList<>();
    }

    public Client(int id, String login, String password) {
        this(login, password);
        this.id = id;
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
    }

    public void addCreditCard() {
        creditCards.add(new CreditCard());
    }

    public CreditCard getCreditCardByAccount(long account) {
        for (CreditCard creditCard : creditCards) {
            if (creditCard.getAccount() == account) {
                return creditCard;
            }
        }
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }
}
