package co.morillas.blockchain;

import java.util.Objects;

public class Transaction {
    private String id;
    private String payload;
    private String hash;

    public Transaction(String id, String payload, String hash) {
        this.id = id;
        this.payload = payload;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(payload, that.payload) &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, payload, hash);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", payload='" + payload + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
