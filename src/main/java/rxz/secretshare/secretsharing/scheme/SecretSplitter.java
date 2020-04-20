package rxz.secretshare.secretsharing.scheme;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.crt.CRTSecretSplitter;
import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.dto.SplitRequestDTO;
import rxz.secretshare.secretsharing.scheme.shamir.ShamirSecretSplitter;

import java.util.List;

public class SecretSplitter {

    public static List<Share> splitSecret(SplitRequestDTO request) throws ServerException {
        switch (request.getScheme()) {
            case 1:
                return ShamirSecretSplitter.splitSecret(request);
            case 2:
                return CRTSecretSplitter.splitSecret(request);
            default:
                throw new ServerException("Scheme not recognized");
        }
    }

}
