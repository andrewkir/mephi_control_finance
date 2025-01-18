package main.models;

import java.util.List;

public class Wallets {

    private List<Wallet> wallets;

    public Wallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
}
