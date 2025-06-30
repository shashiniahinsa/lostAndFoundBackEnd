package lk.ijse.cmjd109.lostAndFoundSystem.dao;

import lk.ijse.cmjd109.lostAndFoundSystem.entities.RequestEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDao extends JpaRepository<RequestEntity, String> {
    List<RequestEntity> findByStatus(RequestStatus status);

}
