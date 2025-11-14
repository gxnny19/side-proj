package com.jgg.side_proj.mapper;

import com.jgg.side_proj.entity.OnbidEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OnbidEntityMapper {
    
    @Insert("INSERT INTO onbid_item (cltr_mnmt_no, cltr_no, cltr_nm, dpsl_mtd_cd, dpsl_mtd_nm, " +
            "ctgr_hirk_id, ctgr_hirk_nm, ctgr_hirk_id_mid, ctgr_hirk_nm_mid, sido, dtl_addr, goods_price) " +
            "VALUES (#{cltrMnmtNo}, #{cltrNo}, #{cltrNm}, #{dpslMtdCd}, #{dpslMtdNm}, " +
            "#{ctgrHirkId}, #{ctgrHirkNm}, #{ctgrHirkIdMid}, #{ctgrHirkNmMid}, #{sido}, #{dtlAddr}, #{goodsPrice})")
    void insert(OnbidEntity entity);
    
    @Select("SELECT * FROM onbid_item")
    List<OnbidEntity> findAll();
    
    @Select("SELECT * FROM onbid_item WHERE sido = #{sido}")
    List<OnbidEntity> findBySido(String sido);
    
    @Select("SELECT COUNT(*) > 0 FROM onbid_item WHERE cltr_mnmt_no = #{cltrMnmtNo}")
    boolean existsByCltrMnmtNo(String cltrMnmtNo);
}