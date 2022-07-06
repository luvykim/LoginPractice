package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifyService {
    @Autowired
    private final MemberRepository memberRepository;

    // public boolean modify(ModifyRequest modifyRequest) {
    // Member modifyMember = memberRepository.modifyMember(modifyRequest.getUsername(),
    // modifyRequest.getPassword(), modifyRequest.getUpdatedPassword());
    // return true;
    // }
}
