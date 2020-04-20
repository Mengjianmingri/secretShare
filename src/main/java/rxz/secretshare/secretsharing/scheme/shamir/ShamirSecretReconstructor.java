package rxz.secretshare.secretsharing.scheme.shamir;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.shamir.domain.ShamirShare;
import rxz.secretshare.secretsharing.scheme.shamir.dto.ShamirReconstructRequestDTO;
import rxz.secretshare.secretsharing.util.MathUtils;
import rxz.secretshare.secretsharing.scheme.SecretTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShamirSecretReconstructor {

    private static List<? extends Number> x;
    private static List<List<? extends Number>> y;
    private static long k;


    public static String reconstruct(ShamirReconstructRequestDTO reconstructRequest) throws ServerException {
        List<Number> secret = reconstruct(reconstructRequest.getShares(), reconstructRequest.getType() == 3);
        for (int i = 0; i < secret.size(); i++) {
            if(MathUtils.isApproximatelyLong(secret.get(i).doubleValue())) {
                secret.set(i, Math.round(secret.get(i).doubleValue()));
            }
        }
        return SecretTransformer.transformToSecret(reconstructRequest.getType(), secret);
    }

    public static List<Number> reconstruct(List<ShamirShare> shares, boolean isString) {
        k = shares.size();
        x = buildXVector(shares, isString);
        y = buildYVector(shares, isString);
        List<Number> secret = getCurrentVector(0, isString);
        for (int i = 1; i < k; i++) {
            secret = MathUtils.addVectors(secret, getCurrentVector(i, isString));
        }
        return secret;
    }

    private static List<Number> getCurrentVector(int i, boolean isString) {
        List<? extends Number> currentY = y.get(i);
        double product;
        if(!isString) {
            product = 1.0;
            for (int j = 0; j < k; j++) {
                if(j != i) {
                    product *= (-x.get(j).doubleValue()) / (x.get(i).doubleValue() - x.get(j).doubleValue());
                }
            }
        } else {
            long top = 0L;
            long bottom = 0L;
            for (int j = 0; j < k; j++) {
                if(j != i) {
                    top += -x.get(j).longValue();
                    bottom += x.get(i).longValue() - x.get(j).longValue();
                }
            }
            product = (double)top / (double)bottom;
        }
        return MathUtils.multiplyByScalar(product, currentY);
    }

    private static List<List<? extends Number>> buildYVector(List<ShamirShare> shares, boolean isString) {
        List<List<? extends Number>> result = new ArrayList<>();
        if(isString) {
            for (ShamirShare share : shares) {
                List<Long> longs = new ArrayList<>();
                for (Number value : share.getValue()) {
                    longs.add(value.longValue());
                }
                result.add(longs);
            }
        } else {
            result.addAll(shares.stream().map(ShamirShare::getValue).collect(Collectors.toList()));
        }
        return result;
    }

    private static List<? extends Number> buildXVector(List<ShamirShare> shares, boolean isString) {
        if(isString) {
            return shares.stream().map(share -> Math.round(share.getX())).collect(Collectors.toList());
        } else {
            List<Double> result = new ArrayList<>();
            for (ShamirShare share : shares) {
                result.add(share.getX());
            }
            return result;
        }
    }

}
