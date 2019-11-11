package co.morillas.blockchain.util;

import co.morillas.blockchain.Transaction;
import org.junit.Before;
import org.junit.Test;
import se.mockachino.Mockachino;
import se.mockachino.annotations.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static se.mockachino.Mockachino.verifyExactly;
import static se.mockachino.Mockachino.verifyOnce;
import static se.mockachino.Mockachino.when;
import static se.mockachino.matchers.Matchers.any;

public class MerkleTreeTest {
    private MerkleTree merkleTree;

    @Mock
    HashManager hashManagerMock;

    @Before
    public void setUp() {
        Mockachino.setupMocks(this);

        merkleTree = new MerkleTree(hashManagerMock);
    }

    @Test
    public void addOneTransaction() {
        Transaction transaction = new Transaction("id", "payload", "hash");

        merkleTree.addTransaction(transaction);
        String root = merkleTree.getMerkleRoot();

        assertThat(root, is("hash"));
    }

    @Test
    public void addSecondTransaction() {
        Transaction transaction1 = new Transaction("id1", "payload1", "hash1");
        Transaction transaction2 = new Transaction("id2", "payload2", "hash2");

        when(hashManagerMock.calculateHash(any(String.class))).thenReturn("root");

        merkleTree.addTransaction(transaction1);
        merkleTree.addTransaction(transaction2);
        String root = merkleTree.getMerkleRoot();

        assertThat(root, is("root"));
        verifyOnce().on(hashManagerMock).calculateHash(any(String.class));
    }

    @Test
    public void addThirdTransaction() {
        Transaction transaction1 = new Transaction("id1", "payload1", "hash1");
        Transaction transaction2 = new Transaction("id2", "payload2", "hash2");
        Transaction transaction3 = new Transaction("id3", "payload3", "hash3");

        when(hashManagerMock.calculateHash(any(String.class)))
                .thenReturn("node1")
                .thenReturn("node2")
                .thenReturn("root");

        merkleTree.addTransaction(transaction1);
        merkleTree.addTransaction(transaction2);
        merkleTree.addTransaction(transaction3);
        String root = merkleTree.getMerkleRoot();

        assertThat(root, is("root"));
        verifyExactly(4).on(hashManagerMock).calculateHash(any(String.class));
    }

    @Test
    public void addFourthTransaction() {
        Transaction transaction1 = new Transaction("id1", "payload1", "hash1");
        Transaction transaction2 = new Transaction("id2", "payload2", "hash2");
        Transaction transaction3 = new Transaction("id3", "payload3", "hash3");
        Transaction transaction4 = new Transaction("id4", "payload4", "hash4");

        when(hashManagerMock.calculateHash(any(String.class)))
                .thenReturn("node1")
                .thenReturn("node2")
                .thenReturn("root");

        merkleTree.addTransaction(transaction1);
        merkleTree.addTransaction(transaction2);
        merkleTree.addTransaction(transaction3);
        merkleTree.addTransaction(transaction4);
        String root = merkleTree.getMerkleRoot();

        assertThat(root, is("root"));
        verifyExactly(10).on(hashManagerMock).calculateHash(any(String.class));
    }
}
