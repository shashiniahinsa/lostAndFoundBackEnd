package lk.ijse.cmjd109.lostAndFoundSystem.controller;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.RequestDto;
import lk.ijse.cmjd109.lostAndFoundSystem.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class RequestController {

    private final RequestService claimRequestService;

    public RequestController(RequestService requestService) {
        this.claimRequestService = requestService;
    }

    @GetMapping
    public String healthCheck() {
        return "Request Controller running";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addRequest(@RequestBody RequestDto claimData) {
        claimRequestService.addRequest(claimData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateRequest(@RequestParam("requestId") String claimId,
            @RequestBody RequestDto claimData)
            throws AccessDeniedException {
        System.out.println("RequestController - Updating request: " + claimId);
        System.out.println("RequestController - DTO received: " + claimData);
        System.out.println("RequestController - ReviewerId: " + claimData.getReviewerId());
        System.out.println("RequestController - Status: " + claimData.getStatus());

        claimRequestService.updateRequest(claimId, claimData);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String claimId) {
        claimRequestService.deleteRequest(claimId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{requestId}")
    public ResponseEntity<RequestDto> getRequest(@PathVariable String claimId) {
        return ResponseEntity.ok(claimRequestService.getRequestById(claimId));
    }

    @GetMapping("getall")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        return ResponseEntity.ok(claimRequestService.getAllRequests());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RequestDto>> getPendingRequests() {
        return ResponseEntity.ok(claimRequestService.getPendingRequests());
    }

}
