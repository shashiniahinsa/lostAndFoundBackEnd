package lk.ijse.cmjd109.lostAndFoundSystem.service.impl;

import lk.ijse.cmjd109.lostAndFoundSystem.dao.ItemDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dao.UserDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.ItemDto;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.ItemEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.UserEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.ItemNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.service.ItemService;
import lk.ijse.cmjd109.lostAndFoundSystem.util.EntityDtoConversion;
import lk.ijse.cmjd109.lostAndFoundSystem.util.UtilityData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao lostFoundRepository;
    private final UserDao reporterRepository;
    private final EntityDtoConversion dataTransformer;

    @Override
    public void addItem(ItemDto lostFoundData) {
        lostFoundData.setItemId(UtilityData.generateItemId());
        lostFoundData.setDateReported(UtilityData.generateTodayDate());

        ItemEntity lostFoundEntity = dataTransformer.toItemEntity(lostFoundData);

        UserEntity reporterProfile = reporterRepository.findById(lostFoundData.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        lostFoundEntity.setReportedBy(reporterProfile);

        lostFoundRepository.save(lostFoundEntity);
    }

    @Override
    public void updateItem(String lostFoundId, ItemDto lostFoundData) {
        ItemEntity existingItem = lostFoundRepository.findById(lostFoundId)
                .orElseThrow(() -> new ItemNotFoundException("Item Not Found"));
        if (lostFoundData.getItemStatus() != null)
            existingItem.setItemStatus(lostFoundData.getItemStatus());
        if (lostFoundData.getName() != null)
            existingItem.setName(lostFoundData.getName());
        if (lostFoundData.getLocation() != null)
            existingItem.setLocation(lostFoundData.getLocation());
        if (lostFoundData.getDescription() != null)
            existingItem.setDescription(lostFoundData.getDescription());

        existingItem.setDateReported(UtilityData.generateTodayDate());

        if (lostFoundData.getUserId() != null) {
            UserEntity reporterProfile = reporterRepository.findById(lostFoundData.getUserId())
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            existingItem.setReportedBy(reporterProfile);
        }

        lostFoundRepository.save(existingItem);
    }

    @Override
    public void deleteItem(String lostFoundId) {
        Optional<ItemEntity> locatedItem = lostFoundRepository.findById(lostFoundId);
        if (locatedItem.isEmpty()) {
            throw new ItemNotFoundException("Item Not Found");
        }
        lostFoundRepository.deleteById(lostFoundId);
    }

    @Override
    public ItemDto getSelectedItem(String lostFoundId) {
        Optional<ItemEntity> locatedItem = lostFoundRepository.findById(lostFoundId);
        if (locatedItem.isEmpty()) {
            throw new ItemNotFoundException("Item Not Found");
        }

        return dataTransformer.toItemDto(lostFoundRepository.getReferenceById(lostFoundId));
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<ItemEntity> inventoryList = lostFoundRepository.findAll();
        return dataTransformer.toItemDtoList(inventoryList);
    }
}
