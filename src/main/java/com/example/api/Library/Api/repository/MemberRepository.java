package com.example.api.Library.Api.repository;

import com.example.api.Library.Api.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long member);

    @Override
    Page<Member> findAll(Pageable pagable);

}
