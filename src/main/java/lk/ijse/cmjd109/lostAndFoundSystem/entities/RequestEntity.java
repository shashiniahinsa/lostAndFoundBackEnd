package lk.ijse.cmjd109.lostAndFoundSystem.entities;

import jakarta.persistence.*;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {
    @Id
    private String requestId;
    private String message;
    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private UserEntity reviewedBy;

    private LocalDate reviewedAt;
}
