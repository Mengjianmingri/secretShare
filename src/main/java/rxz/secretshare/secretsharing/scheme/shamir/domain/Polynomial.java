package rxz.secretshare.secretsharing.scheme.shamir.domain;

import rxz.secretshare.secretsharing.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {

    private final MathUtils.Interval interval;
    private int degree;
    private List<Coefficient> coefficients;

    public Polynomial(int degree, MathUtils.Interval interval) {
        this.degree = degree;
        coefficients = new ArrayList<>();
        this.interval = interval;
    }

    public void add(Coefficient coefficient) {
        coefficients.add(coefficient);
    }

    public List<? extends Number> calculatePolynomialValue(double x) {
        List<? extends Number> result = coefficients.get(0).getValue();
        for (int i = 1; i < coefficients.size(); i++) {
            double scalar = Math.pow(x, i);
            result = MathUtils.addVectors(
                    result,
                    MathUtils.multiplyByScalar(scalar, coefficients.get(i).getValue())
            );
        }
        return result;
    }

    public MathUtils.Interval getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        String result = coefficients.get(0).toString();
        if(coefficients.size() > 1) {
            result += "+" + coefficients.get(1) + "X";
        }
        for (int i = 2; i < degree; i++) {
            result += "+" + coefficients.get(i) + "X^" + i;
        }
        return result;
    }
}
