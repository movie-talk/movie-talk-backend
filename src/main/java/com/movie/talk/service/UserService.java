package com.movie.talk.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.talk.dao.UserDao;
import com.movie.talk.dto.SignUpRequest;
import com.movie.talk.dto.User;
import com.movie.talk.util.UserValidator;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Transactional
    public void insertUser(SignUpRequest signUpRequest) throws Exception {

        if (!UserValidator.isValidId(signUpRequest.getId())) {
            throw new IllegalArgumentException("아이디는 6~12자, 알파벳과 숫자만 포함 가능합니다.");
        }

        if (!UserValidator.isValidPassword(signUpRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상, 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.");
        }

        if (!UserValidator.isValidNickname(signUpRequest.getNickname())) {
            throw new IllegalArgumentException("닉네임은 2~10자, 한글, 영문, 숫자만 포함 가능합니다.");
        }

        if (userDao.getUserById(signUpRequest.getId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String passwordSalt = UUID.randomUUID().toString();
        String passwordHash = hashPassword(signUpRequest.getPassword(), passwordSalt);

        User user = new User();
        user.setId(signUpRequest.getId());
        user.setNickname(signUpRequest.getNickname());
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(passwordSalt);

        userDao.insertUser(user);
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String saltedPassword = password + salt;
        md.update(saltedPassword.getBytes());
        byte[] hashedBytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
