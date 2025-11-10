package com.jgg.side_proj.controller;

import com.jgg.side_proj.model.OnbidResponse;
import com.jgg.side_proj.service.OnbidService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List; // 리스트 타입을 사용할 준비

@RestController
@RequestMapping("/api/onbid") // 모든 주소 앞에 '/api/onbid'가 붙습니다.
public class OnbidController {

    // 💡 1. 재료 전문가(Service)를 주방장(Controller)에 연결합니다.
    private final OnbidService onbidService;

    // 💡 2. 생성자를 통해 연결합니다.
    public OnbidController(OnbidService onbidService) {
        this.onbidService = onbidService;
    }

    // ------------------------------------------------------------------

    // 🔍 3. 캠코 공매 물건 조회 API
    @GetMapping("/search")
    public OnbidResponse searchOnbidItems(
            @RequestParam(required = false) String sido,
            @RequestParam(required = false) String sgk,
            @RequestParam(required = false) String openPriceFrom
    ) {
        // 💡 4. Service를 호출하고, 손님(브라우저)에게 결과를 돌려줍니다.
        return onbidService.searchOnbidItems(sido, sgk, openPriceFrom);
    }
}