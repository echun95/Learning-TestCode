package com.study.testcode.member;


import com.study.testcode.domain.Member;
import com.study.testcode.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
