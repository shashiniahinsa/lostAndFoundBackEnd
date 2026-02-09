package lk.ijse.cmjd109.lostAndFoundSystem.service;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.UserDto;

import java.util.List;

public interface UserService {

    void addUser(UserDto user);

    void updateUser(String userId, UserDto user);

    void deleteUser(String userId);

    UserDto getUserById(String userId);

    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);
}