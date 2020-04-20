package rxz.secretshare.secretsharing.controller;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.SecretSplitter;
import rxz.secretshare.secretsharing.scheme.crt.CRTSecretReconstructor;
import rxz.secretshare.secretsharing.scheme.crt.dto.CRTReconstructRequestDTO;
import rxz.secretshare.secretsharing.scheme.domain.Share;
import rxz.secretshare.secretsharing.scheme.dto.SplitRequestDTO;
import rxz.secretshare.secretsharing.scheme.repository.ShareRepository;
import rxz.secretshare.secretsharing.scheme.shamir.ShamirSecretReconstructor;
import rxz.secretshare.secretsharing.scheme.shamir.dto.ShamirReconstructRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secret-sharing")
@CrossOrigin
public class Controller {

    @RequestMapping(method = RequestMethod.POST, value = "/split", consumes = "application/json")
    public ResponseEntity<String> split(@RequestBody SplitRequestDTO splitRequest) {
        try {
            List<Share> shares = SecretSplitter.splitSecret(splitRequest);
            ShareRepository.buildRepository(shares);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServerException e) {
            return treatException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shares/{id}", produces = "application/json")
    public ResponseEntity getShare(@PathVariable int id){
        try {
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
    public ResponseEntity<String> reconstructCRTSecret(@RequestBody CRTReconstructRequestDTO reconstructRequest){
        try {
            return new ResponseEntity<>(
                    CRTSecretReconstructor.reconstruct(reconstructRequest),
                    HttpStatus.OK
            );
        } catch (ServerException ex) {
            return treatException(ex);
        }
    }

    private ResponseEntity<String> treatException(ServerException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
