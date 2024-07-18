package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.Member;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {
    void addAllMembersToDB() throws IOException;

    List<Member> getAllMembers();

    Optional<Member> findById(Long aLong);

    void saveMember(Member member1);

}
