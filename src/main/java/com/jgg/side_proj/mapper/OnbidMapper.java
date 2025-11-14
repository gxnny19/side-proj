package com.jgg.side_proj.mapper;

import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import org.springframework.stereotype.Component;

@Component
public class OnbidMapper {

    public OnbidEntity toEntity(KamcoItemDto dto) {
        OnbidEntity entity = new OnbidEntity();
        entity.setCltrMnmtNo(dto.getCltrMnmtNo());
        entity.setCltrNm(dto.getCltrNm());
        entity.setSido(dto.getSido());
        entity.setSgk(dto.getSgk());
        entity.setPbctBegDtm(dto.getPbctBegDtm());
        entity.setPbctClsDtm(dto.getPbctClsDtm());
        entity.setMinBidPrc(dto.getMinBidPrc());
        entity.setCtgrFullNm(dto.getCtgrFullNm());
        entity.setPaslAssesAvgAmt(dto.getPaslAssesAvgAmt());
        entity.setPbctClsStatNm(dto.getPbctClsStatNm());
        return entity;
    }

}