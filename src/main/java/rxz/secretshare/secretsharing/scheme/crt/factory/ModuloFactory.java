package rxz.secretshare.secretsharing.scheme.crt.factory;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class ModuloFactory {

    public static List<Long> generateModulos(int n, int t, List<Long> secret) throws ServerException {
        if(t < n / 2.0) {
            t = (int)Math.round(n / 2.0 + 0.001);
        }
        return generateSecureModulosIfPossible(n, t, secret);
    }

    private static List<Long> generateInsecureModulos(int n) {
        List<Long> shares = new ArrayList<>();
        shares.add(1L);
        long current = 2L;
        while(current < Long.MAX_VALUE && shares.size() < n) {
            addInListIfRelativePrimeWithAll(shares, current);
            current++;
        }
        return shares;
    }

    private static List<Long> generateSecureModulosIfPossible(int n, int t, List<Long> secret) throws ServerException {
        long upperBound = calculateUpperBound(n, t, MathUtils.getMinimumValue(secret));
        long lowerBound = calculateLowerBound(t, MathUtils.getMaximumValue(secret));
        List<Long> shares = new ArrayList<>();
        long i = lowerBound;
        for (; i <= upperBound && shares.size() < t; i++) {
            addInListIfRelativePrimeWithAll(shares, i);
        }
        if(i == upperBound + 1) {
            generateRemainingShares(i, shares, n);
        } else {
            for (long j = upperBound; j>=i && shares.size() < n; j--) {
                addInListIfRelativePrimeWithAll(shares, j);
            }
            if(shares.size() != n) {
                generateRemainingShares(upperBound + 1, shares, n);
            }
        }
        return shares;
    }

    private static void generateRemainingShares(long current, List<Long> shares, int n) throws ServerException {
        while(shares.size() < n && current < Long.MAX_VALUE) {
            addInListIfRelativePrimeWithAll(shares, current);
            current++;
        }
        if(shares.size() != n) {
            throw new ServerException("It is not possible to generate the shares with these parameters");
        }
    }

    private static void addInListIfRelativePrimeWithAll(List<Long> shares, long current) {
        if(MathUtils.isRelativePrimeWithAll(shares, current)) {
            shares.add(current);
        }
    }

    private static long calculateLowerBound(int t, long maxValue) {
        return (long)Math.ceil(Math.pow(maxValue, 1.0/t));
    }

    private static long calculateUpperBound(int n, int t, long minValue) {
        return (long) Math.floor(Math.pow(minValue, 1.0 / (n - t)));
    }
}
