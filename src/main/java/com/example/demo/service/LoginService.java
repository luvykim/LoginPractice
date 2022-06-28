package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.MemberRepository;
import com.example.demo.vo.LoginRequest;
import com.example.demo.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    @Autowired
    private final MemberRepository memberRepository;

    public boolean login(LoginRequest loginRequest) {

        Member findMember =
                memberRepository.findMember(loginRequest.getUsername(), loginRequest.getPassword());

        if (findMember == null) {
            return false;

        }

        if (!findMember.getUser_password().equals(loginRequest.getPassword())) {
            return false;
        }
        return true;

    }
}
