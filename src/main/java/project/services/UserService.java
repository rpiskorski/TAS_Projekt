package project.services;

import project.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addUser(User user);

    public Optional<User> getUsersByName(String name);
    public void deleteUser(int id);
    public User getUserById(int id);
    public List<User> getAllUsers();
}
