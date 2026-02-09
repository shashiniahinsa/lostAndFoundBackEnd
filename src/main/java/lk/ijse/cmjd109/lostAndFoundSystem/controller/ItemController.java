package lk.ijse.cmjd109.lostAndFoundSystem.controller;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.ItemDto;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.ItemNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService lostFoundItemService;

    public ItemController(ItemService itemService) {
        this.lostFoundItemService = itemService;
    }

    @GetMapping
    public String healthtest() {
        return "Item Controller runing";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Void> addItem(@RequestBody ItemDto lostFoundData) {
        lostFoundItemService.addItem(lostFoundData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String lostFoundId) {
        try {
            lostFoundItemService.deleteItem(lostFoundId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ItemNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception generalException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @PatchMapping
    public ResponseEntity<Void> updateItem(@RequestParam("itemId") String lostFoundId,
            @RequestBody ItemDto lostFoundData) {
        System.out.println("Updating item: " + lostFoundData.getItemId());
        System.out.println("Reported by userId: " + lostFoundData.getUserId());

        try {
            lostFoundItemService.updateItem(lostFoundId, lostFoundData);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ItemNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception generalException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping(value = "{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> getSelectedItem(@PathVariable String lostFoundId) {
        try {
            return ResponseEntity.ok(lostFoundItemService.getSelectedItem(lostFoundId));
        } catch (ItemNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception generalException) {
            generalException.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getAllItems() {

        return ResponseEntity.ok(lostFoundItemService.getAllItems());
    }

}