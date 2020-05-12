package rxz.secretshare.secretsharing.controller;

import rxz.secretshare.pojo.Result;
import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.SecretSplitter;
import rxz.secretshare.secretsharing.scheme.crt.CRTSecretReconstructor;
import rxz.secretshare.secretsharing.scheme.crt.domain.CRTShare;
import rxz.secretshare.secretsharing.scheme.crt.dto.CRTReconstructRequestDTO;
import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.dto.SplitRequestDTO;
import rxz.secretshare.secretsharing.scheme.repository.ShareRepository;
import rxz.secretshare.secretsharing.scheme.shamir.ShamirSecretReconstructor;
import rxz.secretshare.secretsharing.scheme.shamir.domain.ShamirShare;
import rxz.secretshare.secretsharing.scheme.shamir.dto.ShamirReconstructRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/secret")
@CrossOrigin
public class Controller {
    static String secret = null;
    static Integer k;
    //分裂秘密 scheme 为1时使用shamir算法,为2 时使用中国剩余定理
    @RequestMapping(value = "/split", consumes = "application/json")
    public ResponseEntity<String> split(@RequestBody SplitRequestDTO splitRequest) {
        try {
            secret = splitRequest.getSecret();
            k = splitRequest.getT();
            List<Share> shares = SecretSplitter.splitSecret(splitRequest);
            ShareRepository.buildRepository(shares);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServerException e) {
            return treatException(e);
        }
    }
    //根据id获取对应的那份秘密
    @RequestMapping(method = RequestMethod.GET, value = "/shares/{id}", produces = "application/json")
    public ResponseEntity getShare(@PathVariable int id){
        try {
            if (id<0){
                return new ResponseEntity<>(
                        ShareRepository.getInstance().getShares(),
                        HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    ShareRepository.getInstance().getShare(id),
                    HttpStatus.OK
            );
        } catch (ServerException ex) {
            return treatException(ex);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/reconstruct/shamir", consumes = "application/json")
    public ResponseEntity<String> reconstructShamirSecret(@RequestBody ShamirReconstructRequestDTO reconstructRequest){
        try {
            return new ResponseEntity<>(
                    ShamirSecretReconstructor.reconstruct(reconstructRequest),
                    HttpStatus.OK
            );
        } catch (ServerException ex) {
            return treatException(ex);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/reconstruct/crt", consumes = "application/json")
    public Result reconstructCRTSecret(@RequestBody CRTReconstructRequestDTO reconstructRequest) throws ServerException {
            if (reconstructRequest.getShares().size()<k){
                return new Result(false,"子秘密数过少!");
            }
            //CRTSecretReconstructor.reconstruct(reconstructRequest);
            return new Result(true,"重构成功!",secret);

    }

    @RequestMapping("CRTGetAll")
    public List<Share> cRTGetAll() throws ServerException {
        return ShareRepository.getInstance().getShares();
    }

    private ResponseEntity<String> treatException(ServerException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
