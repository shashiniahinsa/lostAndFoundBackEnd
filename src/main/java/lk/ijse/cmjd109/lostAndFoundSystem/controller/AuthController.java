package lk.ijse.cmjd109.lostAndFoundSystem.controller;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.UserDto;
import lk.ijse.cmjd109.lostAndFoundSystem.security.JwtUtil;
import lk.ijse.cmjd109.lostAndFoundSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserService accountService;
    private final AuthenticationManager credentialManager;
    private final JwtUtil tokenUtil;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserDto registrationData) {
        try {
            if (registrationData.getRole() == null) {
                registrationData.setRole(lk.ijse.cmjd109.lostAndFoundSystem.enums.Role.USER);
            }

            if (registrationData.getRole() == lk.ijse.cmjd109.lostAndFoundSystem.enums.Role.ADMIN) {
                System.out.println("Warning: Creating ADMIN user: " + registrationData.getUsername());
            }

            System.out
                    .println("Signup attempt for user: " + registrationData.getUsername() + " with role: "
                            + registrationData.getRole());

            accountService.addUser(registrationData);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "username", registrationData.getUsername(),
                    "role", registrationData.getRole().toString()));
        } catch (Exception registrationException) {
            System.out.println("Signup failed for user: " + registrationData.getUsername() + ", Error: "
                    + registrationException.getMessage());
            return ResponseEntity.status(400)
                    .body(Map.of("error", "Registration failed: " + registrationException.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentialsMap) {
        System.out.println("Login attempt received for user: " + credentialsMap.get("username"));

        String accountName = credentialsMap.get("username");
        String accountPassword = credentialsMap.get("password");

        try {
            credentialManager.authenticate(new UsernamePasswordAuthenticationToken(accountName, accountPassword));
            System.out.println("Authentication successful for user: " + accountName);

            UserDto authenticatedUser = accountService.getUserByUsername(accountName);
            String accessToken = tokenUtil.generateToken(authenticatedUser.getUserId(), accountName,
                    authenticatedUser.getRole().name());

            System.out.println("Generated JWT token for user: " + accountName + " (ID: " + authenticatedUser.getUserId()
                    + ") with role: " + authenticatedUser.getRole().name());

            return ResponseEntity.ok(Map.of("token", accessToken));
        } catch (Exception authenticationException) {
            System.out.println("Authentication failed for user: " + accountName + ", Error: "
                    + authenticationException.getMessage());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}
