package rxz.secretshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rxz.secretshare.pojo.Result;
import rxz.secretshare.pojo.User;
import rxz.secretshare.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Result login(String username , String password){
        try {
            if (username!=null&&password!=null){
                User user = new User(username,password);
                User user1 = userService.login(user);
                if (user1!=null){
                    return new Result(true,"登录成功!",user1);
                }else {
                    return new Result(true,"用户名或密码错误!");
                }
            }else return new Result(true,"用户名或密码不能为空!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"服务器忙,请稍后再试~");
        }
    }

    @RequestMapping("/register")
    public Result regieter(String username , String password){
        try {
            if (username!=null&&password!=null){
                User user = new User(username,password);
                userService.register(user);
                return new Result(true,"注册成功!",user);
            }else {
                return new Result(true,"用户名或密码不能为空!");
            }
        }catch (Exception e){
            return new Result(false,"用户名已被注册,换个昵称试试!");
        }
    }

}
