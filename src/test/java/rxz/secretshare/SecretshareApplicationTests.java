package rxz.secretshare;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import rxz.secretshare.pojo.SecretShare;
import rxz.secretshare.core.Shamir;

import java.math.BigInteger;

class SecretshareApplicationTests {

    @Test
    void contextLoads() {
        final Shamir shamir = new Shamir(11, 20);
        final BigInteger secret = new BigInteger("99999");
        final SecretShare[] shares = shamir.split(secret);
        SecretShare[] secretShares = new SecretShare[12];
        for (int i = 0; i < secretShares.length; i++) {
            secretShares[i]= shares[i];
        }
        Shamir shamir1 = new Shamir(12, 0);
        final BigInteger prime = shamir.getPrime();
        final BigInteger result = shamir1.combine(secretShares, prime);
    }

}
