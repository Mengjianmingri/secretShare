package rxz.secretshare.secretsharing.scheme.shamir;

import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.dto.SplitRequestDTO;
import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.shamir.domain.Polynomial;
import rxz.secretshare.secretsharing.scheme.shamir.factory.PolynomialFactory;
import rxz.secretshare.secretsharing.scheme.shamir.factory.ShareFactory;
import rxz.secretshare.secretsharing.scheme.SecretTransformer;

import java.util.List;

public class ShamirSecretSplitter {

    public static List<Share> splitSecret(SplitRequestDTO splitRequest) throws ServerException {
        List<Double> secret = SecretTransformer.transformSecretToDoubleList(splitRequest.getType(), splitRequest.getSecret());
        Polynomial polynomial = PolynomialFactory.buildPolynomial(secret, splitRequest.getT());
        System.out.println("The polynomial built is: " + polynomial);
        return ShareFactory.generateShares(polynomial, splitRequest.getN(), splitRequest.getType() == 2);
    }

}
