package com.jgg.side_proj.controller;

import com.jgg.side_proj.dto.BidRequestDto;
import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.LoginRequestDto;
import com.jgg.side_proj.dto.RegisterRequestDto;
import com.jgg.side_proj.dto.SearchRequestDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.entity.UserEntity;
import com.jgg.side_proj.repository.UserRepository;
import com.jgg.side_proj.service.OnbidService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OnbidController {

    private final OnbidService onbidService;
    private final UserRepository userRepository;

    public OnbidController(OnbidService onbidService, UserRepository userRepository) {
        this.onbidService = onbidService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/auction.html");
    }

    @GetMapping("/search")
    @ResponseBody
    public Map<String, Object> search(@RequestParam(required = false) String sido,
                                      @RequestParam(required = false) String sgk,
                                      @RequestParam(required = false) Integer numOfRows) {
        System.out.println("검색 요청 - 지역: " + sido);
        return onbidService.fetchAndSaveApiDataBySido(sido);
    }
    
    @PostMapping("/search")
    @ResponseBody
    public KamcoApiResponse searchByDto(@RequestBody SearchRequestDto request) {
        return onbidService.search(request);
    }
    
    @GetMapping("/saved")
    @ResponseBody
    public List<OnbidEntity> getSavedItems(@RequestParam String sido) {
        return onbidService.getSavedItems(sido);
    }
    
    @PostMapping("/gyeongbuk")
    @ResponseBody
    public Map<String, Object> saveGyeongbukData() {
        System.out.println("경상북도 버튼 클릭 - API 데이터 가져오기 시작");
        return onbidService.fetchAndSaveApiData();
    }
    
    @GetMapping("/test-gyeongbuk")
    @ResponseBody
    public String testGyeongbukApi() {
        return onbidService.testGyeongbukApi();
    }
    
    @PostMapping("/bid")
    @ResponseBody
    public ResponseEntity<Map<String, String>> submitBid(@RequestBody BidRequestDto bidRequest, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        
        String user = (String) session.getAttribute("user");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.badRequest().body(response);
        }
        
        bidRequest.setBidderName(user);
        return onbidService.submitBid(bidRequest);
    }
    
    @GetMapping("/my-bids")
    @ResponseBody
    public List<Map<String, Object>> getMyBids(HttpSession session) {
        String user = (String) session.getAttribute("user");
        if (user == null) {
            return new java.util.ArrayList<>();
        }
        return onbidService.getMyBids(user);
    }
    
    @DeleteMapping("/bid/{cltrMnmtNo}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> cancelBid(@PathVariable String cltrMnmtNo) {
        return onbidService.cancelBid(cltrMnmtNo);
    }
    
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(@RequestBody RegisterRequestDto registerRequest) {
        Map<String, String> response = new HashMap<>();
        
        try {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                response.put("status", "error");
                response.put("message", "이미 존재하는 아이디입니다.");
                return response;
            }
            
            UserEntity user = new UserEntity();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(registerRequest.getPassword());
            user.setName(registerRequest.getName());
            user.setCreatedAt(java.time.LocalDateTime.now());
            
            userRepository.save(user);
            
            // 콘솔에 회원가입 정보 출력
            System.out.println("=== 회원가입 완료 ===");
            System.out.println("아이디: " + registerRequest.getUsername());
            System.out.println("비밀번호: " + registerRequest.getPassword());
            System.out.println("이름: " + registerRequest.getName());
            System.out.println("====================");
            
            response.put("status", "success");
            response.put("message", "회원가입이 완료되었습니다.");
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "회원가입 중 오류가 발생했습니다.");
        }
        
        return response;
    }
    
    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(@RequestBody LoginRequestDto loginRequest, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        
        try {
            UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
            
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                session.setAttribute("user", loginRequest.getUsername());
                response.put("status", "success");
                response.put("message", "로그인 성공");
            } else {
                response.put("status", "error");
                response.put("message", "아이디 또는 비밀번호가 잘못되었습니다.");
            }
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "로그인 중 오류가 발생했습니다.");
        }
        
        return response;
    }
    
    @PostMapping("/logout")
    @ResponseBody
    public Map<String, String> logout(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        session.invalidate();
        response.put("status", "success");
        response.put("message", "로그아웃 성공");
        return response;
    }
    
    @GetMapping("/check-login")
    @ResponseBody
    public Map<String, Object> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String user = (String) session.getAttribute("user");
        
        if (user != null) {
            response.put("loggedIn", true);
            response.put("username", user);
        } else {
            response.put("loggedIn", false);
        }
        
        return response;
    }
}
