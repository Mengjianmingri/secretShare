package rxz.secretshare.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import rxz.secretshare.pojo.User;

@Repository
@Mapper
public interface UserDao {
    User login(User user);

    int register(User user);

}
