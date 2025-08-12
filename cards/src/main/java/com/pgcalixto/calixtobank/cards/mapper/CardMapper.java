package com.pgcalixto.calixtobank.cards.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pgcalixto.calixtobank.cards.dto.CardDto;
import com.pgcalixto.calixtobank.cards.entity.Card;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CardMapper {

    CardDto mapToCardDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "cardId", ignore = true)
    void updateCard(CardDto cardDto, @MappingTarget Card card);

}
