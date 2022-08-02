package edu.neu.spring.beans;

public class UserService {

    private String id;
    private String name;
    private IUserDao userDao;

    public String queryUserName() {
        if (userDao != null) {
            return userDao.queryUserName(id);
        }
        return "tesDaoI is null";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IUserDao getTestDaoI() {
        return userDao;
    }

    public void setTestDaoI(IUserDao userDao) {
        this.userDao = userDao;
    }
}
