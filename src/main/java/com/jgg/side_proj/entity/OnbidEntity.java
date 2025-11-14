package com.jgg.side_proj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "onbid_item")
@Getter @Setter
public class OnbidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cltrMnmtNo;
    private String cltrNm;
    private String sido;
    private String sgk;
    private String pbctBegDtm;
    private String pbctClsDtm;
    private String minBidPrc;
    private String ctgrFullNm;
    private String paslAssesAvgAmt;
    private String pbctClsStatNm;
}