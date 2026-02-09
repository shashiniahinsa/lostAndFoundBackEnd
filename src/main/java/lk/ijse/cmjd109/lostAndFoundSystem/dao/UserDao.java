package lk.ijse.cmjd109.lostAndFoundSystem.dao;

import lk.ijse.cmjd109.lostAndFoundSystem.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

}
