package rxz.secretshare.secretsharing.scheme.shamir.domain;

import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.util.StringUtils;

import java.util.List;

public class ShamirShare implements Share {

    private double x;
    private List<? extends Number> value; //P(x)

    public ShamirShare() {

    }

    public ShamirShare(double x, List<? extends Number> value) {
        this.x = x;
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public List<? extends Number> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("x: %s\ny: %s", x, StringUtils.vectorToString(value));
    }
}
