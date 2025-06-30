package lk.ijse.cmjd109.lostAndFoundSystem.entities;

import jakarta.persistence.*;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    private String itemId;
    private String name;
    private String description;
    private ItemStatus itemStatus;
    private String location;
    private LocalDate dateReported;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity reportedBy;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<RequestEntity> requests;


}

