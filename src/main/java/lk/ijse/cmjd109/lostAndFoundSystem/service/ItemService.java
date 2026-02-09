package lk.ijse.cmjd109.lostAndFoundSystem.service;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.ItemDto;

import java.util.List;

public interface ItemService {
    void addItem(ItemDto item);

    void updateItem(String itemId, ItemDto item);

    void deleteItem(String itemId);

    ItemDto getSelectedItem(String itemId);

    List<ItemDto> getAllItems();
}
