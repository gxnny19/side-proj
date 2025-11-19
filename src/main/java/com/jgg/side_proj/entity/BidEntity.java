package com.jgg.side_proj.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BidEntity {
    private Long id;
    private String cltrMnmtNo;
    private String bidderName;
    private Long bidAmount;
    private String bidStatus;
    private LocalDateTime bidTime;
    private LocalDateTime createdAt;
}