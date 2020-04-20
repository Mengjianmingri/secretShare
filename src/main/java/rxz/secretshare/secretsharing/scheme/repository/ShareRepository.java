package rxz.secretshare.secretsharing.scheme.repository;

import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.exception.ServerException;

import java.util.List;

public class ShareRepository {

    private static ShareRepository INSTANCE;
    private List<Share> shares;

    private ShareRepository(List<Share> shares) {
        this.shares = shares;
    }

    public static void buildRepository(List<Share> shares) {
        INSTANCE = new ShareRepository(shares);
    }

    public static ShareRepository getInstance() throws ServerException {
        if(INSTANCE != null) {
            return INSTANCE;
        } else {
            throw new ServerException("No split was done");
        }
    }

    public Share getShare(int index) throws ServerException {
        if(index >= shares.size()) {
            throw new ServerException("No share with this id");
        }
        return shares.get(index);
    }

}
