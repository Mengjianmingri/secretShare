package rxz.secretshare.secretsharing.util;

import java.util.Random;

public class RandomGenerator {

    public static Double generateRandomDoubleFromInterval(MathUtils.Interval interval) {
        double randomValue = getRandomValueFromInterval(interval);
        return MathUtils.roundToThreeDecimals(randomValue);
    }

    private static double getRandomValueFromInterval(MathUtils.Interval interval) {
        Random r = new Random();
        double random = interval.min + (interval.max - interval.min) * r.nextDouble();
        return r.nextDouble() > 0.4 ? random : -random;
    }

    public static Long generateRandomLongFromInterval(MathUtils.Interval interval) {
        return Math.round(getRandomValueFromInterval(interval));
    }

    public static Double generateX(MathUtils.Interval interval) {
        Random r = new Random();
        double rand = interval.min + (interval.max - interval.min) * r.nextDouble();
        if(r.nextDouble() > 0.1) {
            return Math.floor(rand * 100.0) / 100.0;
        } else {
            return (double)Math.round(rand);
        }
    }

    public static double generateLongX(MathUtils.Interval interval) {
        Random r = new Random();
        double rand = interval.min + (interval.max - interval.min) * r.nextDouble();
        return Math.round(rand);
    }
}
