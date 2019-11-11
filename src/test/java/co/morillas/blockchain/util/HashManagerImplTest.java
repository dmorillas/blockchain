package co.morillas.blockchain.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HashManagerImplTest {
    @Test
    public void calculateHashWorksAsExpected() {
        String test = "THIS IS A TEST";
        String expectedHash = "f6b51a03debf680bdcc215f429eed499ef5933e759614da25ed44513e918561d";

        HashManager hashManager = new HashManagerImpl();
        String hash = hashManager.calculateHash(test);

        assertThat(hash, is(expectedHash));
    }
}
