package rxz.secretshare.secretsharing.scheme.crt.domain;

import rxz.secretshare.secretsharing.scheme.domain.Share;

import java.util.List;

public class CRTShare implements Share {

    private Long modulo;

    private List<Long> value;

    public CRTShare() {}

    public CRTShare(List<Long> value) {
        this.value = value;
    }

    public Long getModulo() {
        return modulo;
    }

    public void setModulo(Long modulo) {
        this.modulo = modulo;
    }

    public List<Long> getValue() {
        return value;
    }

    public void setValue(List<Long> value) {
        this.value = value;
    }
}
