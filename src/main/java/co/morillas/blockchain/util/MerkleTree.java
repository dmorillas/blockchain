package co.morillas.blockchain.util;

import co.morillas.blockchain.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private String merkleRoot;
    private List<String> transactions;

    private HashManager hashManager;

    public MerkleTree(HashManager hashManager) {
        this.hashManager = hashManager;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction.getHash());

        calculateMerkleRoot();
    }

    private void calculateMerkleRoot() {
        List<String> previousTreeLayer = new ArrayList<>(transactions);
        List<String> treeLayer = new ArrayList<>(previousTreeLayer);
        int nodes = previousTreeLayer.size();

        while(nodes > 1) {
            treeLayer = new ArrayList<>();

            for(int i = 1; i < previousTreeLayer.size(); i++) {
                treeLayer.add(hashManager.calculateHash(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
            }

            previousTreeLayer = new ArrayList<>(treeLayer);
            nodes = previousTreeLayer.size();
        }

        merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }
}
