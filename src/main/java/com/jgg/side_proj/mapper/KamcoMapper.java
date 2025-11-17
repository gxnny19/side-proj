package com.jgg.side_proj.mapper;

import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class KamcoMapper {

    public OnbidEntity toEntity(KamcoItemDto dto) {
        if (dto == null) {
            return null;
        }
        
        OnbidEntity entity = new OnbidEntity();
        entity.setRnum(dto.getRnum());
        entity.setPlnmNo(dto.getPlnmNo());
        entity.setPbctNo(dto.getPbctNo());
        entity.setPbctCdtnNo(dto.getPbctCdtnNo());
        entity.setCltrNo(dto.getCltrNo());
        entity.setCltrHstrNo(dto.getCltrHstrNo());
        entity.setScrnGrpCd(dto.getScrnGrpCd());
        entity.setCtgrFullNm(dto.getCtgrFullNm());
        entity.setBidMnmtNo(dto.getBidMnmtNo());
        entity.setCltrNm(dto.getCltrNm());
        entity.setCltrMnmtNo(dto.getCltrMnmtNo());
        entity.setLdnmAdrs(dto.getLdnmAdrs());
        entity.setNmrdAdrs(dto.getNmrdAdrs());
        entity.setLdnmPnu(dto.getLdnmPnu());
        entity.setDpslMtdCd(dto.getDpslMtdCd());
        entity.setDpslMtdNm(dto.getDpslMtdNm());
        entity.setBidMtdNm(dto.getBidMtdNm());
        entity.setMinBidPrc(dto.getMinBidPrc());
        entity.setApslAsesAvgAmt(dto.getApslAsesAvgAmt());
        entity.setFeeRate(dto.getFeeRate());
        entity.setPbctBegnDtm(dto.getPbctBegnDtm());
        entity.setPbctClsDtm(dto.getPbctClsDtm());
        entity.setPbctCltrStatNm(dto.getPbctCltrStatNm());
        entity.setUscbdCnt(dto.getUscbdCnt());
        entity.setIqryCnt(dto.getIqryCnt());
        entity.setGoodsNm(dto.getGoodsNm());
        entity.setCreatedAt(LocalDateTime.now());
        
        return entity;
    }
}