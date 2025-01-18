package main;

import main.models.User;
import main.utils.UsersManager;
import main.utils.WalletManager;

import java.util.InputMismatchException;
import java.util.Scanner;

import static main.utils.WalletManager.DEFAULT_CATEGORY;

public class Main {
    public static void main(String[] args) {
        User user;
        do {
            user = handleAuth();
        } while (user == null);

        mainMenu(user);
    }

    private static User handleAuth() {
        User user = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Для начала работы с сервисом нужно войти или зарегистрироваться");
            System.out.println("1 - Вход");
            System.out.println("2 - Регистрация");
            System.out.println("3 - Завершение работы");

            String next = scanner.nextLine();
            switch (next) {
                case "1":
                    String loginUsername;
                    while (true) {
                        System.out.println("Введите логин:");
                        loginUsername = scanner.nextLine();
                        if (loginUsername.isBlank()) {
                            System.out.println("Логин не может быть пустым");
                        } else {
                            break;
                        }
                    }
                    String loginPassword;
                    while (true) {
                        System.out.println("Введите пароль:");
                        loginPassword = scanner.nextLine();
                        if (loginPassword.isBlank()) {
                            System.out.println("Пароль не может быть пустым");
                        } else {
                            break;
                        }
                    }
                    User loginUser = UsersManager.loginUser(loginUsername, loginPassword);
                    if (loginUser != null) {
                        user = loginUser;
                        System.out.println("Вы успешно вошли");
                    } else {
                        System.out.println("Неверный пароль");
                    }
                    break;
                case "2":
                    String username;
                    while (true) {
                        System.out.println("Введите логин:");
                        username = scanner.nextLine();
                        if (username.isBlank()) {
                            System.out.println("Логин не может быть пустым");
                        } else {
                            break;
                        }
                    }
                    String password;
                    while (true) {
                        System.out.println("Введите пароль:");
                        password = scanner.nextLine();
                        if (password.isBlank()) {
                            System.out.println("Пароль не может быть пустым");
                        } else {
                            break;
                        }
                    }
                    if (UsersManager.registerUser(username, password)) {
                        System.out.println("Вы успешно зарегистрировались");
                        User registerUser = UsersManager.loginUser(username, password);
                        if (registerUser != null) user = registerUser;
                    } else {
                        System.out.println("Логин уже существует");
                    }
                    break;

                case "3":
                    return null;

                default:
                    System.out.println("Введите корректную команду");
                    continue;
            }

            if (user == null) {
                System.out.println("Не удалось войти, повторите попытку\n");
            } else {
                break;
            }
        }

        return user;
    }

    private static void mainMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Доступные команды");
            System.out.println("1 - Список расходов");
            System.out.println("2 - Список доходов");
            System.out.println("3 - Добавить расход");
            System.out.println("4 - Добавить доход");
            System.out.println("5 - Добавить бюджет на расходы");
            System.out.println("6 - Завершение работы");

            String next = scanner.nextLine();
            switch (next) {
                case "1":
                    handleExpensesList(user);
                    break;
                case "2":
                    handleIncomesList(user);
                    break;
                case "3":
                    handleAddExpense(user);
                    break;
                case "4":
                    handleAddIncome(user);
                    break;
                case "5":
                    addBudgetToCategory(user);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Введите корректную команду");
                    break;
            }
        }
    }

    private static void handleExpensesList(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Доступные команды");
        System.out.println("1 - Список всех расходов по категориям");
        System.out.println("2 - Список расходов по конкретной категории");

        String next = scanner.nextLine();
        switch (next) {
            case "1":
                WalletManager.getAllExpenses(user);
                break;

            case "2":
                System.out.println("Введите категорию (пустая для общих расходов)");
                String category = scanner.nextLine();
                if (category.isBlank()) category = DEFAULT_CATEGORY;
                WalletManager.getExpenses(user, category);
                break;

            default:
                System.out.println("Введите корректную команду");
                break;
        }
    }

    private static void handleIncomesList(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Доступные команды");
        System.out.println("1 - Список всех доходов по категориям");
        System.out.println("2 - Список доходов по конкретной категории");

        String next = scanner.nextLine();
        switch (next) {
            case "1":
                WalletManager.getAllIncomes(user);
                break;

            case "2":
                System.out.println("Введите категорию (пустая для общих доходов)");
                String category = scanner.nextLine();
                if (category.isBlank()) category = DEFAULT_CATEGORY;
                WalletManager.getIncomes(user, category);
                break;

            default:
                System.out.println("Введите корректную команду");
                break;
        }
    }

    private static void handleAddExpense(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите категорию расхода (пустой для общих расходов)");
        String category = scanner.nextLine();
        if (category == null || category.isBlank()) category = DEFAULT_CATEGORY;
        int amount;
        while (true) {
            System.out.println("Введите расход:");
            do {
                try {
                    amount = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка. Введите число");
                    scanner.nextLine();
                    amount = -1;
                }
            } while (amount == -1);
            scanner.nextLine();

            if (amount <= 0) {
                System.out.println("Расход не может быть меньше или равен 0");
            } else {
                break;
            }
        }

        WalletManager.addExpense(user, category, amount);
    }

    private static void handleAddIncome(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите категорию дохода (пустой для общих доходов)");
        String category = scanner.nextLine();
        if (category == null || category.isBlank()) category = DEFAULT_CATEGORY;
        int amount;
        while (true) {
            System.out.println("Введите доход:");
            do {
                try {
                    amount = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка. Введите число");
                    scanner.nextLine();
                    amount = -1;
                }
            } while (amount == -1);
            scanner.nextLine();

            if (amount <= 0) {
                System.out.println("Доход не может быть меньше или равен 0");
            } else {
                break;
            }
        }

        WalletManager.addIncome(user, category, amount);
    }

    private static void addBudgetToCategory(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите категорию расходов, для которой нужно задать бюджет:");
        String category = scanner.nextLine();

        int amount;
        while (true) {
            System.out.println("Введите бюджет:");
            do {
                try {
                    amount = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка. Введите число");
                    scanner.nextLine();
                    amount = -1;
                }
            } while (amount == -1);
            scanner.nextLine();

            if (amount <= 0) {
                System.out.println("Бюджет не может быть меньше или равен 0");
            } else {
                break;
            }
        }

        WalletManager.addBudget(user, category, amount);
    }
}