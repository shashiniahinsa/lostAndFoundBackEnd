package lk.ijse.cmjd109.lostAndFoundSystem.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.ItemDto;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.RequestDto;
import lk.ijse.cmjd109.lostAndFoundSystem.dto.UserDto;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.ItemEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.RequestEntity;
import lk.ijse.cmjd109.lostAndFoundSystem.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityDtoConversion {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(ItemEntity.class, ItemDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getReportedBy().getUserId(), ItemDto::setUserId);
        });
    }

    public ItemDto toItemDto(ItemEntity item) {
        return modelMapper.map(item, ItemDto.class);
    }

    public ItemEntity toItemEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, ItemEntity.class);
    }

    public List<ItemDto> toItemDtoList(List<ItemEntity> items) {
        return modelMapper.map(items, new TypeToken<List<ItemDto>>() {
        }.getType());
    }

    public UserDto toUserDto(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserEntity toUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public List<UserDto> toUserDtoList(List<UserEntity> users) {
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    public RequestDto toRequestDto(RequestEntity request) {
        RequestDto dto = modelMapper.map(request, RequestDto.class);
        dto.setItemId(request.getItem().getItemId());
        dto.setUserId(request.getUser().getUserId());

        if (request.getReviewedBy() != null) {
            dto.setReviewerId(request.getReviewedBy().getUserId());
        }

        return dto;
    }

    public RequestEntity toRequestEntity(RequestDto dto) {
        return modelMapper.map(dto, RequestEntity.class);
    }

    public List<RequestDto> toRequestDtoList(List<RequestEntity> requests) {
        return requests.stream()
                .map(this::toRequestDto)
                .toList();
    }
}
