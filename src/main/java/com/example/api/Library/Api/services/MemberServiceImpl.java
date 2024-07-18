package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    public void addAllMembersToDB() throws IOException {
        String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/members.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if(!line.trim().isEmpty()) {
                Member member = new Member();
                member.setName(line);
                saveMember(member);
            }
        }
    }

    @Override
    public Page<Member> getAllMembers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return memberRepository.findAll(pageable);
    }

    @Override
    public Optional<Member> findById(Long aLong) {
        return memberRepository.findById(aLong);
    }

    @Override
    public void saveMember(Member member1) {
        memberRepository.save(member1);
    }

    @Override
    public boolean readMember() {
        return memberRepository.existsById(1L);
    }
}
