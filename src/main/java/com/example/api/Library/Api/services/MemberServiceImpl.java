package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/members.txt";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookService bookService;

    public List<Member> readMembersFromFile() throws IOException {
        List<Member> members = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            Member member = new Member();
            String parts[] = line.trim().split(",");
            if(parts.length>=1) {
                member.setName(parts[0]);
                Book book=bookService.findByTitle(parts[1]);
                member.setBook((List<Book>) book);
            }
            members.add(member);
        }

        reader.close();
        return members;

    }

    public void addAllMembersToDB() throws IOException {
        List<Member> members = readMembersFromFile();
        for (Member member : members) {
            memberRepository.save(member);
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
}
