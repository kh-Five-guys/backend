package com.finalproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.dto.UserDTO;
import com.finalproject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 회원 삭제
    @DeleteMapping("/members/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        int result = service.deleteUser(userId);
        if (result > 0) {
            return ResponseEntity.ok("회원 삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 삭제 실패");
        }
    }

    // 회원 등급 변경
    @PostMapping("/members/{userId}/rank")
    public ResponseEntity<String> updateUserRank(@PathVariable String userId, @RequestBody Map<String, Integer> rankData) {
        int newRankNo = rankData.get("rankNo");
        System.out.println(newRankNo);
        int result = service.updateUserRank(userId, newRankNo);
        if (result > 0) {
            return ResponseEntity.ok("회원 등급 변경 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 등급 변경 실패");
        }
    }
    
    //회원 수정  (수정해야함, 실행은 됌 gpt 사용)
    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(
            @RequestPart("nickName") String nickName,
            @RequestPart("currentPassword") String currentPassword,
            @RequestPart(value = "newPassword", required = false) String newPassword,
            @RequestPart(value = "address", required = false) String address,
            @RequestPart(value = "extraAddress", required = false) String extraAddress,
            @RequestPart(value = "detailAddress", required = false) String detailAddress,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            HttpSession session) {

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String userId = currentUser.getUserId();

        UserDTO user = service.login(userId, currentPassword);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호를 다시 확인해주세요.");
        }

        user.setUserNick(nickName);
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setUserPasswd(newPassword);
        }
        if (address != null && !address.isEmpty()) {
            user.setUserAddress(address);
        }
        // extraAddress와 detailAddress를 사용하여 추가 로직을 구현할 수 있음

        // 프로필 이미지 업로드 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 저장 로직 추가
        }

        service.updateUser(user);

        // 세션 업데이트
        session.setAttribute("user", user);

        return ResponseEntity.ok("변경 완료");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session, HttpServletRequest request) {
        String id = loginData.get("id");
        String passwd = loginData.get("passwd");
        
        UserDTO dto = service.login(id, passwd);
        
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID or password.");
        } else {
            
            session.invalidate();
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", dto);
            return ResponseEntity.ok().body(dto); // 로그인 성공 시 사용자 정보를 반환
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        System.out.println("진입 확인부터");
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            service.insertUser(userDTO);
            return ResponseEntity.ok("회원가입에 성공하셨습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }
    
    @GetMapping("/members")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
