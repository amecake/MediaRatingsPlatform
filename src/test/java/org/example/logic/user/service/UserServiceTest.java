package org.example.logic.user.service;

import org.example.logic.user.model.User;
import org.example.logic.user.persistence.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void register_fails_not_unique_username() {

        // Make real object for testing
        User newUser = new User("merlin", "pw");

        // Simulate behavior: if username is "merlin", return false
        // This is just how we define it, what we want.
        // It doesn't actually need to check it
        when(userRepository.isUniqueUsername("merlin")).thenReturn(false);

        // This is the actual thing we are testing.
        // It now calls the real method
        boolean result = userService.register(newUser);

        // This is what the correct output would be
        // You assume it's false
        // Does the method behave as expected?
        assertFalse(result);

        // This is to check if no inserts into DB actually happened?
        verify(userRepository, never()).register(any());
    }

    @Test
    void register_success_with_unique_username() {
        // Now check the opposite
        User newUser = new User("merlin", "pw");

        // If the username is unique, it should return true
        when(userRepository.isUniqueUsername("merlin")).thenReturn(true);

        // When it does return true, pretend, it returns ID = 1 from database
        // This is just fake behavior
        // This is basically just the WHAT SHOULD HAPPEN?
        when(userRepository.register(newUser)).thenReturn(1);

        // Now call the method that is being tested
        boolean result = userService.register(newUser);

        // Now check for correct behavior
        assertTrue(result);
        assertEquals(1, newUser.getId());

        // Check if the method was actually called
        verify(userRepository).register(newUser);
    }

    @Test
    void log_in_username_doesnt_exist() {

        User checkUser = new User("amelia", "pw");

        // When "amelia" isn't in user database yet, return false
        when(userRepository.usernameExists("amelia")).thenReturn(false);

        String result = userService.credentialsCheck(checkUser);

        assertEquals("Username doesn't exist", result);

        verify(userRepository).usernameExists(checkUser.getUsername());
        verify(userRepository, never()).usernameMatchPw(any(), any());
    }

    @Test
    void log_in_username_pw_dont_match() {
        User checkUser = new User("amelia", "pw");

        when(userRepository.usernameExists("amelia")).thenReturn(true);
        when(userRepository.usernameMatchPw("amelia", "pw")).thenReturn(false);

        String result = userService.credentialsCheck(checkUser);

        assertEquals("Password doesn't match username", result);

        verify(userRepository).usernameExists("amelia");
        verify(userRepository).usernameMatchPw("amelia", "pw");
    }

    @Test
    void log_in_success() {
        User checkUser = new User("amelia", "pw");

        when(userRepository.usernameExists("amelia")).thenReturn(true);
        when(userRepository.usernameMatchPw("amelia", "pw")).thenReturn(true);

        String result = userService.credentialsCheck(checkUser);

        assertEquals("success", result);

        verify(userRepository).usernameExists("amelia");
        verify(userRepository).usernameMatchPw("amelia", "pw");
    }
}
