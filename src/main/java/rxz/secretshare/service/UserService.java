package rxz.secretshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rxz.secretshare.dao.UserDao;
import rxz.secretshare.pojo.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User login(User user){
        return userDao.login(user);
    }

    public Integer register(User user) {
        return userDao.register(user);
    }
}
