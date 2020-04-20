package rxz.secretshare.secretsharing.scheme.shamir.factory;

import rxz.secretshare.secretsharing.scheme.shamir.domain.Coefficient;
import rxz.secretshare.secretsharing.scheme.shamir.domain.Polynomial;
import rxz.secretshare.secretsharing.util.MathUtils;
import rxz.secretshare.secretsharing.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import static rxz.secretshare.secretsharing.util.MathUtils.Interval;

public class PolynomialFactory {

    private static Interval interval;
    private static boolean areAllLong;

    private static void determineInterval(List<Double> secret) {
        areAllLong = MathUtils.areAllLong(secret);
        if(areAllLong) {
            interval = MathUtils.computeLongInterval(secret);
        } else {
            interval = MathUtils.computeDoubleInterval(secret);
        }
    }

    private static Coefficient buildCoefficient(List<Double> value) {
        return new Coefficient(value);
    }

    public static Polynomial buildPolynomial(List<Double> secret, int degree) {
        int size = secret.size();
        determineInterval(secret);
        Polynomial polynomial = new Polynomial(degree, interval);
        polynomial.add(buildCoefficient(secret));
        for (int i = 1; i < degree; i++) {
            List<Double> value = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Double number;
                if(areAllLong) {
                    number = RandomGenerator.generateRandomLongFromInterval(interval).doubleValue();
                } else {
                    number = RandomGenerator.generateRandomDoubleFromInterval(interval);
                }
                value.add(number);
            }
            polynomial.add(buildCoefficient(value));
        }
        return polynomial;
    }

}
