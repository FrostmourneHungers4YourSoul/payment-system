package org.example.payment.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.payment.api.dto.CreateUserRequest;
import org.example.payment.api.dto.UserDto;
import org.example.payment.application.mapper.PaymentEntityMapper;
import org.example.payment.infrastructure.persistence.UserEntity;
import org.example.payment.infrastructure.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PaymentEntityMapper mapper;

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        boolean exists = userRepository.existsByEmailIgnoreCase(request.email());
        if (exists) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "User already exists: " + request.email()
            );
        }
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        UserEntity saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserDto getUserOrThrow(UUID id) {
        UserEntity user = userRepository.findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.getAllWithPayments().stream()
            .map(this::toResponse)
            .toList();
    }

    private UserDto toResponse(UserEntity user) {
        return new UserDto(
            user.getId(),
            user.getEmail(),
            mapper.mapListToDto(user.getPayments())
        );
    }
}
