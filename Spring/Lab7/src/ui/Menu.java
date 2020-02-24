package ui;

import banking.Client;
import banking.CreditCard;
import storage.PaymentSystemFactory;
import storage.base.PaymentSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String ENTER_LOGIN_STR = "Введите логин: ";
    private static final String ENTER_PASSWORD_STR = "Введите пароль: ";
    private static final String ENTER_ACCOUNT_STR = "Введите номер счёта: ";

    private Scanner in = new Scanner(System.in);

    private PaymentSystem paymentSystem;
    private Client currentClient = null;

    public Menu() {
        paymentSystem = PaymentSystemFactory.getPaymentSystem(PaymentSystemFactory.PaymentSystemType.STAX_XML);
    }

    public void openMenu() {
        startMenuCycle();
    }

    private long inputLongValue() {
        long k;
        try {
            k = in.nextLong();
        } catch (InputMismatchException ex) {
            logger.warn(ex.getMessage());
            k = -1;
        }
        return k;
    }

    private void startMenuCycle() {
        while (true) {
            showStartMenu();
            int k = (int) inputLongValue();
            switch (k) {
                case 0:
                    paymentSystem.saveChanges();
                    return;
                case 1:
                    enter();
                    break;
                case 2:
                    register();
                    break;
                default:
                    System.out.println("Неправильный номер пункта меню!");
            }
        }
    }

    private void enter() {
        System.out.print(ENTER_LOGIN_STR);
        String login = in.next();
        System.out.print(ENTER_PASSWORD_STR);
        String password = in.next();
        if (login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD)) {
            adminMenuCycle();
            return;
        }
        Client client = paymentSystem.findClientByLogin(login);
        if (client == null) {
            System.out.println("Клиента с таким логином не существует!");
        } else if (client.getPassword().equals(password)) {
            currentClient = client;
            clientMenuCycle();
        } else {
            System.out.println("Неправильный пароль!");
        }
    }

    private void register() {
        String login, password, repeatedPassword;
        do {
            System.out.print(ENTER_LOGIN_STR);
            login = in.next();
        } while (paymentSystem.findClientByLogin(login) != null);
        do {
            System.out.print(ENTER_PASSWORD_STR);
            password = in.next();
            System.out.print("Повторите пароль: ");
            repeatedPassword = in.next();
        } while (!password.equals(repeatedPassword));
        currentClient = new Client(login, password);
        paymentSystem.addClient(currentClient);
    }

    private void clientMenuCycle() {
        while (true) {
            showClientMenu();
            int k = (int) inputLongValue();
            switch (k) {
                case 0:
                    currentClient = null;
                    return;
                case 1:
                    currentClient.addCreditCard();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    freezeAccount();
                    break;
                case 4:
                    makeDeposit();
                    break;
                case 5:
                    showAccounts();
                    break;
                default:
                    System.out.println("Неправильный номер пункта меню!");
            }
        }
    }

    private void makePayment() {
        System.out.print(ENTER_ACCOUNT_STR);
        long account = inputLongValue();
        CreditCard creditCard = currentClient.getCreditCardByAccount(account);
        if (creditCard != null) {
            System.out.print("Введите сумму платежа: ");
            long amount = inputLongValue();
            if (creditCard.makePayment(amount)) {
                System.out.println("Платёж успешно совершён!");
            } else {
                System.out.println("Платёж не совершён по неизвестной причине!");
            }
        } else {
            System.out.println("Счёт не существует!");
        }
    }

    private void freezeAccount() {
        System.out.print(ENTER_ACCOUNT_STR);
        long account = inputLongValue();
        CreditCard creditCard = currentClient.getCreditCardByAccount(account);
        if (creditCard != null) {
            creditCard.freeze();
            System.out.println("Счёт заблокирован!");
        } else {
            System.out.println("Счёт не существует!");
        }
    }

    private void makeDeposit() {
        System.out.print(ENTER_ACCOUNT_STR);
        long account = inputLongValue();
        CreditCard creditCard = currentClient.getCreditCardByAccount(account);
        if (creditCard != null) {
            System.out.print("Введите сумму пополнения: ");
            long amount = inputLongValue();
            if (creditCard.makeDeposit(amount)) {
                System.out.println("Пополнение счёта прошло успешно!");
            } else {
                System.out.println("Ошибка пополнения счёта!");
            }
        } else {
            System.out.println("Счёт не существует!");
        }
    }

    private void showAccounts() {
        ArrayList<CreditCard> cards = currentClient.getCreditCards();
        System.out.println("Список ваших кредитных карт");
        for (CreditCard card : cards) {
            System.out.printf("Счёт: %d, Баланс: %d, Заблокирован: %s\n", card.getAccount(),
                    card.getBalance(), card.isFreezed() ? "Да" : "Нет");
        }
    }

    private void adminMenuCycle() {
        while (true) {
            showAdminMenu();
            int k = (int) inputLongValue();
            switch (k) {
                case 0:
                    return;
                case 1:
                    unfreezeAccount();
                    break;
                default:
                    System.out.println("Неправильный номер пункта меню!");
            }
        }
    }

    private void unfreezeAccount() {
        System.out.print(ENTER_ACCOUNT_STR);
        long account = inputLongValue();
        CreditCard card = paymentSystem.findCreditCardByAccount(account);
        if (card != null) {
            card.unfreeze();
            System.out.println("Счёт успешно разблокирован!");
        } else {
            System.out.println("Счёт не существует!");
        }
    }

    private void show(MenuItems t) {
        pause();
        clear();
        System.out.println("Меню");
        t.showItems();
        System.out.println("0. Выйти");
        System.out.print("Введите номер пункта меню: ");
    }

    private void showStartMenu() {
        show(() -> {
            System.out.println("1. Войти");
            System.out.println("2. Зарегистрироваться");
        });
    }

    private void showClientMenu() {
        show(() -> {
            System.out.println("1. Добавить кредитную карту");
            System.out.println("2. Сделать платёж");
            System.out.println("3. Заблокировать счёт");
            System.out.println("4. Пополнить счёт");
            System.out.println("5. Показать все счета");
        });
    }

    private void showAdminMenu() {
        show(() -> System.out.println("1. Разблокировать счёт"));
    }

    private static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            logger.warn(ex.getMessage());
        }
    }

    private static void pause() {
        try {
            new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private interface MenuItems {
        void showItems();
    }
}
