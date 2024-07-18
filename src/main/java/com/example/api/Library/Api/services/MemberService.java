package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface MemberService {
    void addAllMembersToDB() throws IOException;

    Page<Member> getAllMembers(int pageNo, int pageSize);

    Optional<Member> findById(Long aLong);

    void saveMember(Member member1);

    boolean readMember();
}
