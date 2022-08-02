package edu.neu.spring.beans;

public interface IUserDao {
    String queryUserName(String id);
    String deleteUser(String id);
    String addUser(String id, String name);
}
