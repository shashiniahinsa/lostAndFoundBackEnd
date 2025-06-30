package lk.ijse.cmjd109.lostAndFoundSystem.dto;

import lk.ijse.cmjd109.lostAndFoundSystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements Serializable {
    private String userId;
    private String username;
    private String email;
    private String password;
    private Role role;
    private LocalDate joinedDate;
    private String reportedItems;
    private String requests;
}
