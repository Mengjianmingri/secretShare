package rxz.secretshare.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rxz.secretshare.core.YiHuo;
import rxz.secretshare.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/YH")
public class YiHuoController {
    @RequestMapping("/split")
    public Result split(Integer n, Integer k, Integer secret){
        try {
            List<Integer> shares = YiHuo.split(secret, n);
            return new Result(true,"解构成功!",shares);
        }catch (Exception e){
            return new Result(false,"解构失败!");
        }
    }

    @RequestMapping("/reconstruct")
    public Result reconstruct(@RequestBody List<Integer> shares){
        try {
            Integer secret = YiHuo.reconstruct(shares);
            return new Result(true,"重构成功!",secret);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"重构失败!");
        }
    }
}
