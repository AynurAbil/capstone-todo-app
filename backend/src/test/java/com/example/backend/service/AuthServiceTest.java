package com.example.backend.service;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    @Description("Register/Create new user")
    void create_user_and_return_token() {
        RegisterRequest request = new RegisterRequest(
                "John Doe",
                "john.doe@example.com",
                "test123"
        );

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(passwordEncoder.encode("test123")).thenReturn("encodedPassword");
        when(jwtService.generateToken("john.doe@example.com")).thenReturn("mock-jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("User registered successfully", response.getMessage());

        verify(userRepository, times(1)).existsByEmail("john.doe@example.com");
        verify(passwordEncoder, times(1)).encode("test123");
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken("john.doe@example.com");
    }

    @Test
    @Description("Prevent duplicate registration")
    void register_throws_exception_when_email_already_exists() {
        RegisterRequest request = new RegisterRequest(
                "John Doe",
                "john.doe@example.com",
                "test123"
        );

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        assertEquals("Email is already registered", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail("john.doe@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    @Description("Login success")
    void login_return_token_when_credentials_are_valid() {
        LoginRequest request = new LoginRequest("john.doe@example.com", "test123");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("test123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("john.doe@example.com")).thenReturn("mock-jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("Login successful", response.getMessage());

        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
        verify(passwordEncoder, times(1)).matches("test123", "encodedPassword");
        verify(jwtService, times(1)).generateToken("john.doe@example.com");
    }

    @Test
    @Description("Login fails when user not found")
    void login_throws_exception_when_user_does_not_exist() {
        LoginRequest request = new LoginRequest("missing@example.com", "test123");

        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("missing@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    @Description("Login fails with invalid password)")
    void login_throws_exception_when_password_invalid() {
        LoginRequest request = new LoginRequest("john.doe@example.com", "wrongpass");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encodedPassword")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
        verify(passwordEncoder, times(1)).matches("wrongpass", "encodedPassword");
        verify(jwtService, never()).generateToken(anyString());
    }
}
