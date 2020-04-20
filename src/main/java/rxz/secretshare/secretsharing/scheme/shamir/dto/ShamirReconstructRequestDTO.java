package rxz.secretshare.secretsharing.scheme.shamir.dto;

import rxz.secretshare.secretsharing.scheme.shamir.domain.ShamirShare;

import java.util.List;

public class ShamirReconstructRequestDTO {

    private int type;
    private List<ShamirShare> shares;

    public ShamirReconstructRequestDTO() {}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ShamirShare> getShares() {
        return shares;
    }

    public void setShares(List<ShamirShare> shares) {
        this.shares = shares;
    }
}
