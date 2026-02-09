package lk.ijse.cmjd109.lostAndFoundSystem.service.impl;

import lk.ijse.cmjd109.lostAndFoundSystem.dao.ItemDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dao.RequestDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dao.UserDao;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.RequestDto;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.RequestEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.UserEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.RequestStatus;
import lk.ijse.cmjd109.lostAndFoundSystem.enums.Role;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.ItemNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.RequestNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.exception.UserNotFoundException;
import lk.ijse.cmjd109.lostAndFoundSystem.service.RequestService;
import lk.ijse.cmjd109.lostAndFoundSystem.util.EntityDtoConversion;
import lk.ijse.cmjd109.lostAndFoundSystem.util.UtilityData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestDao claimRepository;
    private final ItemDao itemRepository;
    private final UserDao profileRepository;
    private final EntityDtoConversion dataMapper;

    @Override
    public void addRequest(RequestDto claimData) {
        claimData.setRequestId(UtilityData.generateRequestId());
        claimData.setDateCreated(UtilityData.generateTodayDate());

        RequestEntity claimEntity = dataMapper.toRequestEntity(claimData);

        claimEntity.setItem(itemRepository.findById(claimData.getItemId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found")));
        claimEntity.setUser(profileRepository.findById(claimData.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found")));

        claimRepository.save(claimEntity);
    }

    @Override
    public void updateRequest(String claimId, RequestDto claimData) throws AccessDeniedException {
        RequestEntity existingClaim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RequestNotFoundException("Request not found"));

        if (claimData.getStatus() != null) {
            existingClaim.setStatus(claimData.getStatus());
            existingClaim.setReviewedAt(LocalDate.now());

            if (claimData.getReviewerId() != null && !claimData.getReviewerId().trim().isEmpty()) {
                UserEntity reviewerProfile = profileRepository.findById(claimData.getReviewerId())
                        .orElseThrow(() -> new UserNotFoundException("Reviewer not found"));

                if (reviewerProfile.getRole() != Role.STAFF && reviewerProfile.getRole() != Role.ADMIN) {
                    throw new AccessDeniedException("Only STAFF or ADMIN can review requests.");
                }

                existingClaim.setReviewedBy(reviewerProfile);
                System.out.println("RequestService - Setting reviewer: " + reviewerProfile.getUserId() + " ("
                        + reviewerProfile.getUsername() + ")");
            } else {
                System.out.println("RequestService - No reviewerId provided in update");
            }
        }

        if (claimData.getMessage() != null) {
            existingClaim.setMessage(claimData.getMessage());
        }

        claimRepository.save(existingClaim);
        System.out.println("RequestService - Request updated successfully");
    }

    @Override
    public void deleteRequest(String claimId) {
        if (!claimRepository.existsById(claimId))
            throw new RuntimeException("Request not found");
        claimRepository.deleteById(claimId);
    }

    @Override
    public RequestDto getRequestById(String claimId) {
        RequestEntity claimEntity = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return dataMapper.toRequestDto(claimEntity);
    }

    @Override
    public List<RequestDto> getAllRequests() {
        return dataMapper.toRequestDtoList(claimRepository.findAll());
    }

    @Override
    public List<RequestDto> getPendingRequests() {
        List<RequestEntity> pendingClaims = claimRepository.findByStatus(RequestStatus.PENDING);
        return dataMapper.toRequestDtoList(pendingClaims);
    }

}
