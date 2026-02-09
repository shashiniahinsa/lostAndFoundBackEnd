package lk.ijse.cmjd109.lostAndFoundSystem.entities;

import jakarta.persistence.*;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    private String userId;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate joinedDate;

    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<ItemEntity> reportedItems;

    @OneToMany(mappedBy = "requestId", cascade = CascadeType.ALL)
    private List<RequestEntity> requests;
}
