package rxz.secretshare.secretsharing.scheme.crt;

import rxz.secretshare.secretsharing.scheme.crt.factory.ModuloFactory;
import rxz.secretshare.secretsharing.scheme.crt.factory.ShareFactory;
import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.dto.SplitRequestDTO;
import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.SecretTransformer;

import java.util.List;

public class CRTSecretSplitter {

    public static List<Share> splitSecret(SplitRequestDTO request) throws ServerException {
        List<Long> transformedSecret = SecretTransformer.transformSecretToLongList(request.getType(), request.getSecret());
        List<Long> modulos = ModuloFactory.generateModulos(request.getN(), request.getT(), transformedSecret);
        System.out.println("(" + modulos
                .parallelStream()
                .map(Object::toString)
                .reduce((r, r2) -> r + "," + r2)
                .get() + ")"
        );
        return ShareFactory.generateShares(modulos, transformedSecret);
    }

}
