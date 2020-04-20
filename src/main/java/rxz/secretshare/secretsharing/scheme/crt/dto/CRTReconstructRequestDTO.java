package rxz.secretshare.secretsharing.scheme.crt.dto;

import rxz.secretshare.secretsharing.scheme.crt.domain.CRTShare;

import java.util.List;

public class CRTReconstructRequestDTO {

    private int type;
    private List<CRTShare> shares;

    public CRTReconstructRequestDTO() {}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CRTShare> getShares() {
        return shares;
    }

    public void setShares(List<CRTShare> shares) {
        this.shares = shares;
    }

}
