package com.movie.talk.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.talk.dao.UserDao;
import com.movie.talk.dto.LoginRequest;
import com.movie.talk.dto.SignUpRequest;
import com.movie.talk.dto.User;
import com.movie.talk.util.UserValidator;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    
    public boolean isIdDuplicate(String id) {
        return userDao.getUserById(id) != null;
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userDao.getUserByNickname(nickname) != null;
    }

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

        String passwordSalt = UUID.randomUUID().toString();
        String passwordHash = hashPassword(signUpRequest.getPassword(), passwordSalt);

        User user = new User();
        user.setId(signUpRequest.getId());
        user.setNickname(signUpRequest.getNickname());
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(passwordSalt);

        userDao.insertUser(user);
    }
    
    @Transactional
    public User login(LoginRequest loginRequest) throws Exception {
        if (!UserValidator.isValidId(loginRequest.getId())) {
            throw new IllegalArgumentException("아이디는 6~12자, 알파벳과 숫자만 포함 가능합니다.");
        }

        if (!UserValidator.isValidPassword(loginRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상, 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.");
        }

        User user = userDao.getUserForLogin(loginRequest.getId());
        if (user == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀립니다.");
        }

        String passwordHash = hashPassword(loginRequest.getPassword(), user.getPasswordSalt());
        if (!passwordHash.equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀립니다.");
        }

        return user;
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
