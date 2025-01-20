package main.models;

import java.util.List;
import java.util.Map;

public class Wallet {

    private String username;
    private int currentAmount = 0;
    private Map<String, List<Integer>> expenses;
    private Map<String, List<Integer>> income;
    private Map<String, Integer> budget;


    public Wallet(String username, int currentAmount, Map<String, List<Integer>> expenses, Map<String, List<Integer>> income, Map<String, Integer> budget) {
        this.username = username;
        this.currentAmount = currentAmount;
        this.expenses = expenses;
        this.income = income;
        this.budget = budget;
    }

    public Wallet() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, List<Integer>> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<String, List<Integer>> expenses) {
        this.expenses = expenses;
    }

    public Map<String, List<Integer>> getIncome() {
        return income;
    }

    public void setIncome(Map<String, List<Integer>> income) {
        this.income = income;
    }

    public Map<String, Integer> getBudget() {
        return budget;
    }

    public void setBudget(Map<String, Integer> budget) {
        this.budget = budget;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }
}
