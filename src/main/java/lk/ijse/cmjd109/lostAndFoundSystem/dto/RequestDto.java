package lk.ijse.cmjd109.lostAndFoundSystem.dto;

import lk.ijse.cmjd109.lostAndFoundSystem.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto implements Serializable {
    private String requestId;
    private String message;
    private LocalDate dateCreated;
    private RequestStatus status;
    private String itemId;
    private String userId;
    private String reviewerId;
    private LocalDate reviewedAt;

}
