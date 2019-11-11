package co.morillas.blockchain;

import co.morillas.blockchain.util.HashManager;
import co.morillas.blockchain.util.HashManagerImpl;
import co.morillas.blockchain.util.MerkleTree;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private static int MAX_TX_PER_BLOCK = 10;

    HashManager hashGenerator = new HashManagerImpl();
    MerkleTree merkleTree = new MerkleTree(hashGenerator);

    // Block header
    private String previousHash;
    private String merkleRoot;
    private long timestamp;
    private int nonce;

    private String hash;
    private List<Transaction> transactions;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.merkleRoot = "";
        this.timestamp = System.currentTimeMillis();
        this.nonce = 0;
        this.transactions = new ArrayList<>();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        Gson parser = new Gson();
        String serializedTransactions = parser.toJson(transactions);

        String calculatedhash = hashGenerator.calculateHash(
                previousHash +
                        merkleRoot +
                        Long.toString(timestamp) +
                        Integer.toString(nonce) +
                        serializedTransactions
        );

        return calculatedhash;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    // 1. Calculates hash with data + nonce
    // 2. If hash starts with difficulty, block is mined
    // 3. If not, increases nonce and goes to step 1 again.
    public void mineBlock(String difficulty) {
        while(!hash.startsWith(difficulty)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;

        transactions.add(transaction);
        merkleTree.addTransaction(transaction);
        calculateHash();

        // Check if MAX_TX_PER_BLOCK reached

        System.out.println("Transaction Successfully added to Block");

        return true;
    }
}
