package lk.ijse.cmjd109.lostAndFoundSystem.dto;

import lk.ijse.cmjd109.lostAndFoundSystem.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {

    private String itemId;
    private String name;
    private String description;
    private ItemStatus itemStatus;
    private String location;
    private LocalDate dateReported;
    private String userId;

}
