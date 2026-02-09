package lk.ijse.cmjd109.lostAndFoundSystem.dao;

import lk.ijse.cmjd109.lostAndFoundSystem.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<ItemEntity, String> {
}
