package rxz.secretshare.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储所有可以公开的信息
 */
public class AllPublicInformation implements Serializable {
    private List<SecretShare> secretShares = new ArrayList<>();
    private int k;
    private int n;
    private BigInteger prime;

    @Override
    public String toString() {
        return "AllPublicInformation{" +
                "secretShares=" + secretShares +
                ", k=" + k +
                ", n=" + n +
                ", prime=" + prime +
                '}';
    }

    public List<SecretShare> getSecretShares() {
        return secretShares;
    }

    public void setSecretShares(List<SecretShare> secretShares) {
        this.secretShares = secretShares;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public BigInteger getPrime() {
        return prime;
    }

    public void setPrime(BigInteger prime) {
        this.prime = prime;
    }

    public AllPublicInformation() {
    }

    public AllPublicInformation(List<SecretShare> secretShares, int k, int n, BigInteger prime) {
        this.secretShares = secretShares;
        this.k = k;
        this.n = n;
        this.prime = prime;
    }
}
