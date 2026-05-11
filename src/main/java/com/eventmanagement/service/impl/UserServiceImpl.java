package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.LoginRequestDTO;
import com.eventmanagement.dto.request.UserRequestDTO;
import com.eventmanagement.dto.response.UserResponseDTO;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.service.UserService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DTOMapper mapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already taken");
        }
        User user = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .role(requestDTO.getRole())
                .build();
        return mapper.toUserResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + requestDTO.getEmail()));
        
        if (!user.getPassword().equals(requestDTO.getPassword())) {
            throw new IllegalStateException("Invalid password");
        }
        
        return mapper.toUserResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::toUserResponseDTO);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return mapper.toUserResponseDTO(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (!user.getEmail().equals(requestDTO.getEmail()) && userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already taken");
        }
        
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setRole(requestDTO.getRole());
        
        return mapper.toUserResponseDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
