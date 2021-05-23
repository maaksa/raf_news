package rs.raf.demo.services;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.category.CategoryRepository;
import rs.raf.demo.repositories.user.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public List<User> allUser() {
        return this.userRepository.allUser();
    }

}
