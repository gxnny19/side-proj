package com.jgg.side_proj.mapper;

import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import org.springframework.stereotype.Component;

@Component
public class OnbidEntityDtoMapper {

    public OnbidEntity toEntity(KamcoItemDto dto) {
        if (dto == null) return null;
        
        OnbidEntity entity = new OnbidEntity();
        entity.setCltrMnmtNo(dto.getCltrMnmtNo());
        entity.setCltrNm(dto.getCltrNm());
        entity.setSido(dto.getSido());
        entity.setDtlAddr(dto.getSgk());
        entity.setGoodsPrice(parsePrice(dto.getMinBidPrc()));
        entity.setDpslMtdCd("0001");
        entity.setCtgrHirkId("10000");
        entity.setCtgrHirkIdMid("10100");
        return entity;
    }
    
    private Long parsePrice(String price) {
        if (price == null || price.trim().isEmpty()) return null;
        try {
            return Long.parseLong(price.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}