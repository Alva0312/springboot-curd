package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        Iterable<User> it = userRepository.findAll();
        List<User> users = new ArrayList<>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Long count() {
        return userRepository.count();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User create(User user) {
        User copy = new User(
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
        return userRepository.save(copy);
    }

    public Optional<User> update(Long id, User newItem) {
        return userRepository.findById(id)
                .map(oldItem -> {
                    User updated = oldItem.updateWith(newItem);
                    return userRepository.save(updated);
                });
    }
}
