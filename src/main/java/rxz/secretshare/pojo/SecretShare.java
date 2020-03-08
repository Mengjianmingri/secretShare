package rxz.secretshare.pojo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 存储Shamir加密后的密钥和密钥序号的实体类
 */
public class SecretShare implements Serializable {
    public SecretShare(int num, BigInteger share) {
        this.num = num;
        this.share = share;
    }

    public SecretShare() {
    }

    public int getNum() {
        return num;
    }

    public BigInteger getShare() {
        return share;
    }


    @Override
    public String toString() {
        return "SecretShare [num=" + num + ", share=" + share + "]";
    }

    private  int num;
    private  BigInteger share;
}
