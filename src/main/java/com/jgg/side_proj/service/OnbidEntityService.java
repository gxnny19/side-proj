package com.jgg.side_proj.service;

import com.jgg.side_proj.component.XmlApiClient;
import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.mapper.OnbidEntityDtoMapper;
import com.jgg.side_proj.mapper.OnbidEntityMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnbidEntityService {

    private static final Logger logger = LoggerFactory.getLogger(OnbidEntityService.class);
    
    private final XmlApiClient xmlApiClient;
    private final OnbidEntityMapper onbidEntityMapper;
    private final OnbidEntityDtoMapper dtoMapper;

    public int saveEntities(String sido) {
        try {
            KamcoApiResponse response = xmlApiClient.callKamcoApi(sido);
            
            if (response == null || response.getBody() == null || 
                response.getBody().getItems() == null || 
                response.getBody().getItems().getItem() == null) {
                logger.warn("{} 지역 데이터 없음", sido);
                return 0;
            }

            List<KamcoItemDto> items = response.getBody().getItems().getItem();
            
            int count = 0;
            for (KamcoItemDto dto : items) {
                if (dto != null && dto.getCltrMnmtNo() != null && 
                    !onbidEntityMapper.existsByCltrMnmtNo(dto.getCltrMnmtNo())) {
                    OnbidEntity entity = dtoMapper.toEntity(dto);
                    if (entity != null) {
                        onbidEntityMapper.insert(entity);
                        count++;
                    }
                }
            }
            
            logger.info("{} 지역 {}개 엔티티 저장 완료", sido, count);
            return count;
            
        } catch (Exception e) {
            logger.error("{} 지역 엔티티 수집 실패: {}", sido, e.getMessage());
            return 0;
        }
    }
    
    public List<OnbidEntity> getEntitiesBySido(String sido) {
        return onbidEntityMapper.findBySido(sido);
    }
    
    public List<OnbidEntity> getAllEntities() {
        return onbidEntityMapper.findAll();
    }
}