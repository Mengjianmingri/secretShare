package rxz.secretshare.secretsharing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MathUtils {

    public static class Interval {
        Double min;
        Double max;

        public Interval(Double min, Double max) {
            this.min = min;
            this.max = max;
        }
    }

    public static String numberToString(Number number) {
        String numberString = "";
        if(MathUtils.isApproximatelyLong(number.doubleValue())) {
            numberString += Math.round(number.doubleValue());
        } else {
            numberString += number;
        }
        return numberString;
    }


    public static boolean areAllLong(List<Double> numbers) {
        int i = 0;
        boolean areAllLong = true;
        while(areAllLong && i < numbers.size()) {
            areAllLong = isApproximatelyLong(numbers.get(i++));
        }
        return areAllLong;
    }

    public static boolean isApproximatelyLong(Double number) {
        long numberInLong = Math.round(number);
        return number - numberInLong < 0.01;
    }

    private static Double computeAverage(List<Double> numbers) {
        int count = 0;
        double sum = 0.0;
        for (Double number : numbers) {
            count++;
            sum += number;
        }
        return sum/(double)count;
    }

    public static Interval computeLongInterval(List<Double> numbers) {
        double average = computeAverage(numbers);
        double min = Math.ceil(average/50.0);
        double max = Math.floor(average*2.0);
        return new Interval(min, max);
    }

    public static Interval computeDoubleInterval(List<Double> numbers) {
        double average = computeAverage(numbers);
        double min = average/50.0;
        double max = average * 2.0;
        return new Interval(min, max);
    }

    public static List<Number> addVectors(List<? extends Number> vector1, List<Number> vector2) {
        List<Number> result = new ArrayList<>();
        for (int i = 0; i < vector1.size(); i++) {
            result.add(i, addNumbers(vector1.get(i), vector2.get(i)));
        }
        return result;
    }

    public static List<Number> multiplyByScalar(Number scalar, List<? extends Number> vector) {
        return vector.stream().map(val -> multiplyNumbers(scalar, val)).collect(Collectors.toList());
    }

    private static Number addNumbers(Number number1, Number number2) {
        if(number1 instanceof Long) {
            return number1.longValue() + number2.longValue();
        } else if(number1 instanceof Double) {
            return number1.doubleValue() + number2.doubleValue();
        }
        return null;
    }

    private static Number multiplyNumbers(Number number1, Number number2) {
        if(number1 instanceof Long) {
            return number1.longValue() * number2.longValue();
        } else if(number1 instanceof Double) {
            return number1.doubleValue() * number2.doubleValue();
        }
        return null;
    }

    public static Double roundToThreeDecimals(Double value) {
        return Math.floor(value * 1000.0) / 1000.0;
    }

    public static long gcd(long a, long b)
    {
        if(a == 0 || b == 0) return a+b;
        return gcd(b,a%b);
    }

    public static long getMinimumValue(List<Long> numbers) {
        long min = Long.MAX_VALUE;
        for (Long number : numbers) {
            if(number < min) {
                min = number;
            }
        }
        return min;
    }

    public static long getMaximumValue(List<Long> numbers) {
        long max = Long.MIN_VALUE;
        for (Long number : numbers) {
            if(number > max) {
                max = number;
            }
        }
        return max;
    }

    public static boolean isRelativePrimeWithAll(List<Long> shares, long current) {
        for (Long share : shares) {
            if(gcd(share, current) != 1) {
                return false;
            }
        }
        return true;
    }
}
