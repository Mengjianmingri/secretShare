package rxz.secretshare.secretsharing.scheme.crt.factory;

import rxz.secretshare.secretsharing.scheme.crt.domain.CRTShare;
import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.exception.ServerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShareFactory {


    public static List<Share> generateShares(List<Long> modulos, List<Long> secret) throws ServerException {
        List<Share> shares = new ArrayList<>();
        for (Long modulo : modulos) {
            CRTShare share = new CRTShare();
            share.setValue(secret
                    .parallelStream()
                    .map(value -> value % modulo)
                    .collect(Collectors.toList())
            );
            share.setModulo(modulo);
            shares.add(share);
        }
        return shares;
    }


}
