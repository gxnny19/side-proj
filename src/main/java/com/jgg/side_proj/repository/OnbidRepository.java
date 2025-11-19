package com.jgg.side_proj.repository;

import com.jgg.side_proj.entity.OnbidEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OnbidRepository {
    
    void save(OnbidEntity entity);
    
    List<OnbidEntity> findBySido(String sido);
    
    List<OnbidEntity> findAll();
    
    boolean existsByCltrMnmtNo(String cltrMnmtNo);
    
    boolean existsByCompositeKey(@Param("cltrMnmtNo") String cltrMnmtNo, 
                                 @Param("pbctBegnDtm") String pbctBegnDtm, 
                                 @Param("minBidPrc") Long minBidPrc);
    
    int countBySido(String sido);
    
    void deleteByCltrMnmtNo(String cltrMnmtNo);
    
    OnbidEntity findByCltrMnmtNo(String cltrMnmtNo);
}