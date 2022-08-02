package edu.neu.spring.beans;

import edu.neu.spring.common.UserMap;
import edu.neu.spring.stereotype.Component;

@Component("userDao")
public class UserDao implements IUserDao {

    private String token;
    @Override
    public String queryUserName(String id) {
        return UserMap.getItem(id);
    }

    @Override
    public String deleteUser(String id) {
        UserMap.deleteItem(id);
        return "delete";
    }

    @Override
    public String addUser(String id, String name) {
        UserMap.addItem(id, name);
        return "add";
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "token='" + token + '\'' +
                '}';
    }
}
