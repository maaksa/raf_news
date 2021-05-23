package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.entities.News;
import rs.raf.demo.entities.User;

import java.util.List;

public interface UserRepository {

    public List<User> allUser();

    public User addUser(User user);

    public User updateUser(User user, String email);

}
