package main.utils;

import com.google.gson.Gson;
import main.models.User;
import main.models.Wallet;
import main.models.Wallets;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WalletManager {
    private static final String PATH = "wallets.json";
    public static final String DEFAULT_CATEGORY = "_____default";

    private static Wallets loadWallets() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(PATH)) {
            Wallets wallets = gson.fromJson(reader, Wallets.class);
            reader.close();
            return Objects.requireNonNullElseGet(wallets, () -> new Wallets(Collections.emptyList()));
        } catch (IOException e) {
            try {
                File file = new File(PATH);
                file.createNewFile();
            } catch (IOException exc) {
                System.out.println("Ошибка при получении кошельков");
            }
        }

        return new Wallets(new ArrayList<>(Collections.emptyList()));
    }

    public static void addExpense(User user, String category, int expense) {
        Wallet wallet = getWallet(user);

        Map<String, List<Integer>> expenses = wallet.getExpenses();
        if (expenses == null) expenses = new HashMap<>();
        List<Integer> categoryExpenses = expenses.get(category);
        if (categoryExpenses == null) {
            categoryExpenses = new ArrayList<>(Collections.emptyList());
        }
        categoryExpenses.add(expense);
        expenses.put(category, categoryExpenses);
        wallet.setExpenses(expenses);
        wallet.setUsername(user.getUsername());

        saveWallet(wallet);
        checkIfExceedBudget(user, category);
    }

    public static void addIncome(User user, String category, int income) {
        Wallet wallet = getWallet(user);

        Map<String, List<Integer>> incomes = wallet.getIncome();
        if (incomes == null) incomes = new HashMap<>();
        List<Integer> categoryIncome = incomes.get(category);
        if (categoryIncome == null) {
            categoryIncome = new ArrayList<>(Collections.emptyList());
        }
        categoryIncome.add(income);
        incomes.put(category, categoryIncome);
        wallet.setIncome(incomes);
        wallet.setUsername(user.getUsername());

        saveWallet(wallet);
    }

    public static void getAllExpenses(User user) {
        Wallet wallet = getWallet(user);
        Map<String, List<Integer>> expenses = wallet.getExpenses();
        if (expenses == null) expenses = new HashMap<>();
        int sum = 0;
        for (Map.Entry<String, List<Integer>> entry : expenses.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();
            if (key.equals(DEFAULT_CATEGORY)) {
                key = "Общие расходы (без категории)";
            }
            int expensesSum = value.stream().mapToInt(Integer::intValue).sum();
            sum += expensesSum;
            System.out.println(key + " : " + expensesSum);
        }
        System.out.println("Сумма всех расходов: " + sum);
    }

    public static void getExpenses(User user, String category) {
        Wallet wallet = getWallet(user);
        Map<String, List<Integer>> expenses = wallet.getExpenses();
        if (expenses == null) expenses = new HashMap<>();
        List<Integer> expensesCategory = expenses.get(category);
        if (expensesCategory == null) expensesCategory = new ArrayList<>(Collections.emptyList());
        int sum = 0;
        for (Integer expense : expensesCategory) {
            System.out.println("- " + expense);
            sum += expense;
        }

        System.out.println("Сумма всех расходов по категории: " + sum + "\n");
    }

    public static void getAllIncomes(User user) {
        Wallet wallet = getWallet(user);
        Map<String, List<Integer>> incomes = wallet.getIncome();
        if (incomes == null) incomes = new HashMap<>();
        int sum = 0;
        for (Map.Entry<String, List<Integer>> entry : incomes.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();
            if (key.equals(DEFAULT_CATEGORY)) {
                key = "Общие доходы (без категории)";
            }
            int expensesSum = value.stream().mapToInt(Integer::intValue).sum();
            sum += expensesSum;
            System.out.println(key + " : " + expensesSum);
        }
        System.out.println("Сумма всех доходов: " + sum);
    }

    public static void getIncomes(User user, String category) {
        Wallet wallet = getWallet(user);
        Map<String, List<Integer>> incomes = wallet.getIncome();
        if (incomes == null) incomes = new HashMap<>();
        List<Integer> incomesCategory = incomes.get(category);
        if (incomesCategory == null) incomesCategory = new ArrayList<>(Collections.emptyList());
        int sum = 0;
        for (Integer income : incomesCategory) {
            System.out.println("- " + income);
            sum += income;
        }

        System.out.println("Сумма всех доходов по категории: " + sum + "\n");
    }

    private static void saveWallet(Wallet wallet) {
        List<Wallet> wallets = loadWallets().getWallets();
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            Gson gson = new Gson();
            List<Wallet> result = new ArrayList<>(Collections.emptySet());
            boolean isWalletExist = false;
            if (wallets.isEmpty()) {
                wallets = new ArrayList<>(Collections.emptySet());
                wallets.add(wallet);
            } else {
                for (Wallet innerWallet : wallets) {
                    if (innerWallet.getUsername().equals(wallet.getUsername())) {
                        isWalletExist = true;
                        result.add(wallet);
                    } else {
                        result.add(innerWallet);
                    }
                }
            }
            if (!isWalletExist) {
                result.add(wallet);
            }
            String json = gson.toJson(new Wallets(result));
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении ссылок");
        }
    }

    public static void addBudget(User user, String category, int budget) {
        Wallet wallet = getWallet(user);

        Map<String, Integer> budgets = wallet.getBudget();
        if (budgets == null) budgets = new HashMap<>();
        budgets.put(category, budget);
        wallet.setBudget(budgets);

        saveWallet(wallet);
    }

    private static Wallet getWallet(User user) {
        List<Wallet> wallets = loadWallets().getWallets();
        Wallet wallet = new Wallet();
        for (Wallet innerWallet : wallets) {
            if (innerWallet.getUsername().equals(user.getUsername())) {
                wallet = innerWallet;
            }
        }
        return wallet;
    }

    private static void checkIfExceedBudget(User user, String category) {
        Wallet wallet = getWallet(user);
        Map<String, List<Integer>> expenses = wallet.getExpenses();
        if (expenses == null) expenses = new HashMap<>();
        List<Integer> expensesCategory = expenses.get(category);
        if (expensesCategory == null) expensesCategory = new ArrayList<>(Collections.emptyList());
        int sumExpenses = 0;
        for (Integer expense : expensesCategory) {
            sumExpenses += expense;
        }

        Map<String, Integer> budgets = wallet.getBudget();
        Integer budget;
        if (budgets == null) {
            budget = null;
        } else {
            budget = budgets.get(category);
        }

        if (budget != null && sumExpenses > budget) {
            System.out.println("Вы превысили расход по категории - " + category + "\n");
        }
    }
}
