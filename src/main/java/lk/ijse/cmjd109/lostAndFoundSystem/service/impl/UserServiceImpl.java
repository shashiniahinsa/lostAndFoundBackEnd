package lk.ijse.cmjd109.lostAndFoundSystem.service.impl;

import lk.ijse.cmjd109.lostAndFoundSystem.dao.UserDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.UserDto;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.UserEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.UserNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.service.UserService;
import lk.ijse.cmjd109.lostAndFoundSystem.util.EntityDtoConversion;
import lk.ijse.cmjd109.lostAndFoundSystem.util.UtilityData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao profileRepository;
    private final EntityDtoConversion dataConverter;
    private final PasswordEncoder credentialEncoder;

    @Override
    public void addUser(UserDto profileData) {
        profileData.setUserId(UtilityData.generateUserId());
        profileData.setJoinedDate(UtilityData.generateTodayDate());

        profileData.setPassword(credentialEncoder.encode(profileData.getPassword()));

        UserEntity profileEntity = dataConverter.toUserEntity(profileData);
        profileRepository.save(profileEntity);
    }

    @Override
    public void updateUser(String profileId, UserDto profileData) {
        UserEntity existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (profileData.getUserId() != null)
            existingProfile.setUserId(profileData.getUserId());
        if (profileData.getUsername() != null)
            existingProfile.setUsername(profileData.getUsername());
        if (profileData.getEmail() != null)
            existingProfile.setEmail(profileData.getEmail());
        if (profileData.getPassword() != null) {
            existingProfile.setPassword(credentialEncoder.encode(profileData.getPassword()));
        }
        if (profileData.getRole() != null)
            existingProfile.setRole(profileData.getRole());
        if (profileData.getJoinedDate() != null)
            existingProfile.setJoinedDate(profileData.getJoinedDate());

        profileRepository.save(existingProfile);
    }

    @Override
    public void deleteUser(String profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new UserNotFoundException("User not found");
        }
        profileRepository.deleteById(profileId);
    }

    @Override
    public UserDto getUserById(String profileId) {
        UserEntity profileEntity = profileRepository.findById(profileId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return dataConverter.toUserDto(profileEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> profileList = profileRepository.findAll();
        return dataConverter.toUserDtoList(profileList);
    }

    @Override
    public UserDto getUserByUsername(String accountName) {
        UserEntity profileEntity = profileRepository.findByUsername(accountName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + accountName));
        return dataConverter.toUserDto(profileEntity);
    }
}