package com.jgg.side_proj.dto;

import lombok.Data;

@Data
public class BidRequestDto {
    private String cltrMnmtNo;
    private String bidderName;
    private Long bidAmount;
}