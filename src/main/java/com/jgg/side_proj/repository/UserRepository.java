package com.jgg.side_proj.repository;

import com.jgg.side_proj.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void save(UserEntity user);
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
}