package com.jgg.side_proj.mapper;

import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.model.OnbidItem;
import org.springframework.stereotype.Component;

@Component
public class OnbidDtoMapper {

    public OnbidItem toModel(KamcoItemDto dto) {
        if (dto == null) return null;
        
        OnbidItem item = new OnbidItem();
        item.setCltrMnmtNo(dto.getCltrMnmtNo());
        item.setCltrNm(dto.getCltrNm());
        item.setSido(dto.getSido());
        item.setDtlAddr(dto.getSgk());
        item.setGoodsPrice(parsePrice(dto.getMinBidPrc()));
        item.setDpslMtdCd("0001");
        item.setCtgrHirkId("10000");
        item.setCtgrHirkIdMid("10100");
        return item;
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