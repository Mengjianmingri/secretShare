package rxz.secretshare.core;

import rxz.secretshare.pojo.SecretShare;

import java.math.BigInteger;
import java.util.Random;

public final class Shamir {
    private BigInteger prime;//大素数p
    private final int k;
    private final int n;
    private final Random random;

    private static final int CERTAINTY = 50;
    /**构造
     * @param k 可重构秘密的最小要求子秘密数
     * @param n 分发的子秘密数
     */
    public Shamir(final int k, final int n) {
        this.k = k;
        this.n = n;

        random = new Random();
    }

    public Shamir(int k) {
        this.k = k;
        this.n=k;
        this.random = new Random();
    }

    /**
     * 解构秘密
     * @param secret 秘密本密,即a0
     * @return 解构后的秘密对
     */
    public SecretShare[] split(final BigInteger secret) {
        //modLength:秘密的二进制位数加1
        final int modLength = secret.bitLength() + 1;

        prime = new BigInteger(modLength, CERTAINTY, random);
        final BigInteger[] coeff = new BigInteger[k - 1];

        //System.out.println("Prime Number: " + prime);

        for (int i = 0; i < k - 1; i++) {
            coeff[i] = randomZp(prime);
            //System.out.println("a" + (i + 1) + ": " + coeff[i]);
        }

        final SecretShare[] shares = new SecretShare[n];
        for (int i = 1; i <= n; i++) {//改动
            BigInteger accum = secret;

            for (int j = 1; j < k; j++) {//改动
                final BigInteger t1 = BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), prime);
                final BigInteger t2 = coeff[j - 1].multiply(t1).mod(prime);

                accum = accum.add(t2).mod(prime);
            }
            shares[i - 1] = new SecretShare(i - 1, accum);
            //System.out.println("Share " + shares[i - 1]);
        }

        return shares;
    }

    public BigInteger getPrime() {
        return prime;
    }

    public BigInteger combine(final SecretShare[] shares, final BigInteger primeNum) {
        BigInteger accum = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    num = num.multiply(BigInteger.valueOf(-j - 1)).mod(primeNum);
                    den = den.multiply(BigInteger.valueOf(i - j)).mod(primeNum);
                }
            }

            //System.out.println("den: " + den + ", num: " + den + ", inv: " + den.modInverse(primeNum));
            final BigInteger value = shares[i].getShare();

            final BigInteger tmp = value.multiply(num).multiply(den.modInverse(primeNum)).mod(primeNum);
            accum = accum.add(primeNum).add(tmp).mod(primeNum);

            //System.out.println("value: " + value + ", tmp: " + tmp + ", accum: " + accum);
        }

        //System.out.println("The secret is: " + accum);

        return accum;
    }

    private BigInteger randomZp(final BigInteger p) {
        while (true) {
            final BigInteger r = new BigInteger(p.bitLength(), random);
            if (r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(p) < 0) {
                return r;
            }
        }
    }


}