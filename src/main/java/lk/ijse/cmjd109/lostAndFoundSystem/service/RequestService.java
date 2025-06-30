package lk.ijse.cmjd109.lostAndFoundSystem.service;

import lk.ijse.cmjd109.lostAndFoundSystem.dto.RequestDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface RequestService {
    void addRequest(RequestDto dto);

    void updateRequest(String requestId, RequestDto dto) throws AccessDeniedException;

    void deleteRequest(String requestId);

    RequestDto getRequestById(String requestId);

    List<RequestDto> getAllRequests();

    List<RequestDto> getPendingRequests();
}
