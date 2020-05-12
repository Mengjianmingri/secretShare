package rxz.secretshare.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rxz.secretshare.core.Shamir;
import rxz.secretshare.pojo.AllPublicInformation;
import rxz.secretshare.pojo.Result;
import rxz.secretshare.pojo.SecretShare;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


@RequestMapping("/shamir")
@RestController
public class ShamirController {
    @RequestMapping("/split")
    public Result split(int k , int n , BigInteger secret){
        BigInteger prime = null;
        List<SecretShare> shareList = null;
        if (k==0||n==0||secret==null){
            return new Result(false,"参数错误!");
        }
        try {
            long preTime = System.currentTimeMillis();
            Shamir shamir = new Shamir(k, n);
            SecretShare[] secretShares = shamir.split(secret);
            long aftTime = System.currentTimeMillis();
            prime = shamir.getPrime();
            shareList = Arrays.asList(secretShares);
            return new Result(true,"使用Shamir算法解构成功!共耗时"+(aftTime-preTime)+"毫秒",new AllPublicInformation(shareList,k,n,prime));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"解构失败!");
        }

    }

    @RequestMapping("/reconstruct")
    public Result Reconstruction(@RequestBody AllPublicInformation information){
        int k = information.getK();
        List<SecretShare> secretShares = information.getSecretShares();
        BigInteger prime = information.getPrime();
        if (secretShares==null){
            return new Result(true,"参数不能为空!");
        }
        if (secretShares.size()<k){
            return new Result(true,"子秘密数不足,无法重构!");
        }
        try {
            SecretShare[] shares = new SecretShare[secretShares.size()];
            secretShares.toArray(shares);
            Shamir shamir = new Shamir(k);
            BigInteger secret = shamir.combine(shares, prime);
            return new Result(true,"重构成功",secret);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"重构失败!");
        }
    }
}
