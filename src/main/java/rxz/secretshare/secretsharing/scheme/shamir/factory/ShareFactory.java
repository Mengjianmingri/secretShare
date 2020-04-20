package rxz.secretshare.secretsharing.scheme.shamir.factory;

import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.shamir.domain.Polynomial;
import rxz.secretshare.secretsharing.scheme.shamir.domain.ShamirShare;
import rxz.secretshare.secretsharing.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class ShareFactory {

    public static List<Share> generateShares(Polynomial polynomial, int shareNr, boolean isString) {
        List<Share> shares = new ArrayList<>();
        List<Double> generatedNumbers = new ArrayList<>();
        for (int i = 0; i < shareNr; i++) {
            double x = generateX(polynomial, isString);
            while(generatedNumbers.contains(x)) {
                x = generateX(polynomial, isString);
            }
            generatedNumbers.add(x);
            List<? extends Number> value = polynomial.calculatePolynomialValue(x);
            shares.add(new ShamirShare(x, value));
        }
        return shares;
    }

    private static double generateX(Polynomial polynomial, boolean isString) {
        double x;
        if(isString) {
            x = RandomGenerator.generateX(polynomial.getInterval());
        } else {
            x = RandomGenerator.generateLongX(polynomial.getInterval());
        }
        return x;
    }
}


