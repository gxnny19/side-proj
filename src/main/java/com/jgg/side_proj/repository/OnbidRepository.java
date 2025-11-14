package com.jgg.side_proj.repository;

import com.jgg.side_proj.entity.OnbidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnbidRepository extends JpaRepository<OnbidEntity, Long> {
    boolean existsByCltrMnmtNo(String cltrMnmtNo);
}