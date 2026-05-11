package com.eventmanagement.service;

import com.eventmanagement.dto.request.LoginRequestDTO;
import com.eventmanagement.dto.request.UserRequestDTO;
import com.eventmanagement.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO requestDTO);
    UserResponseDTO login(LoginRequestDTO requestDTO);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO);
    void deleteUser(Long id);
}
