package lk.ijse.cmjd109.lostAndFoundSystem.controller;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.UserDto;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.UserNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService profileService;

    public UserController(UserService userService) {
        this.profileService = userService;
    }

    @GetMapping
    public String healthTest() {
        return "User Controller running";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@RequestBody UserDto profileData) {
        profileService.addUser(profileData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String profileId) {
        try {
            profileService.deleteUser(profileId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception generalException) {
            generalException.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updateUser(@RequestParam("userId") String profileId, @RequestBody UserDto profileData) {
        System.out.println("Updating user: " + profileId);
        System.out.println("Username: " + profileData.getUsername());

        try {
            profileService.updateUser(profileId, profileData);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception generalException) {
            generalException.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable String profileId) {
        try {
            return ResponseEntity.ok(profileService.getUserById(profileId));
        } catch (UserNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception generalException) {
            generalException.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(profileService.getAllUsers());
    }
}
