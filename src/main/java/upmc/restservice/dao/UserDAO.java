package upmc.restservice.dao;

import java.util.List;
import upmc.restservice.dto.User;

public interface UserDAO {
    void addUser(User user);
    User findUserByName(String name);
    User find(String name, String pw);
    void deleteUser(String key);
    void updateUser(User user);
    List<User> listUser();
}