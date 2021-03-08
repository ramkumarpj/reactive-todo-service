package com.rj.example.reactive.todo.DTO.mapper;


import com.rj.example.reactive.todo.DTO.ItemDTO;
import com.rj.example.reactive.todo.data.ItemByEmailId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    ItemDTO itemByEmailIdToItemDTO(ItemByEmailId itemByEmailId);

    ItemByEmailId itemDTOToItemByEmailId(ItemDTO itemDTO);
}
