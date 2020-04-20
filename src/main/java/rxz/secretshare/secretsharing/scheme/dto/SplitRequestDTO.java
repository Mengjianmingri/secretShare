package rxz.secretshare.secretsharing.scheme.dto;

public class SplitRequestDTO {

    private int type;
    private String secret;
    private int n;
    private int t;
    private int scheme;

    public SplitRequestDTO() {
    }

    public SplitRequestDTO(int type, String secret, int n, int t) {
        this.type = type;
        this.secret = secret;
        this.n = n;
        this.t = t;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }
}
