package com.jgg.side_proj.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String name;
    private LocalDateTime createdAt;
}