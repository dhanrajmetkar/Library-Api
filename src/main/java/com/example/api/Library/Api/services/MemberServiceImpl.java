package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/members.txt";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookService bookService;

    public void addAllMembersToDB() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.trim() !="") {
                Member member = new Member();
                member.setName(line);
                saveMember(member);
            }
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long aLong) {
        return memberRepository.findById(aLong);
    }

    @Override
    public void saveMember(Member member1) {
        memberRepository.save(member1);
    }
}
