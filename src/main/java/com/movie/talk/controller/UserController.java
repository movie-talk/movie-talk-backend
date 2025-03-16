package com.movie.talk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movie.talk.dto.LoginRequest;
import com.movie.talk.dto.SignUpRequest;
import com.movie.talk.dto.User;
import com.movie.talk.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            userService.insertUser(signUpRequest);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류, 다시 시도해주세요.");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            User user = userService.login(loginRequest);
            
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                
                return ResponseEntity.ok().body("로그인 성공");
            } else {
                return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀립니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("아이디 또는 비밀번호가 틀립니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류, 다시 시도해주세요.");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
    	try {
			HttpSession session=request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			return ResponseEntity.ok().body("로그아웃 성공");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("서버 오류, 다시 시도해주세요.");
		}
    }

}

