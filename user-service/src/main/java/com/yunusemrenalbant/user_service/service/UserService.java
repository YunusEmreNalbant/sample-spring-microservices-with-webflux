package com.yunusemrenalbant.user_service.service;

import com.yunusemrenalbant.user_service.dto.UserRequest;
import com.yunusemrenalbant.user_service.dto.UserResponse;
import com.yunusemrenalbant.user_service.model.User;
import com.yunusemrenalbant.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı Bulunamadı: " + id));
    }

    public UserResponse create(UserRequest userRequest) {
        User user = new User(userRequest.firstName(), userRequest.lastName(), userRequest.email());

        User createdUser = userRepository.save(user);

        return mapToUserResponse(createdUser);
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + id));

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());

        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser);
    }

    public void delete(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + id));

        userRepository.deleteById(user.getId());
    }

    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
