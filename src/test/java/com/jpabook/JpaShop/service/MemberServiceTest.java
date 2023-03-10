package com.jpabook.JpaShop.service;

import com.jpabook.JpaShop.domain.Member;
import com.jpabook.JpaShop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    void 회원가입_테스트() {
        //given
        Member member = new Member();
        member.setName("Jung");

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));

    }

    @Test
    void 중복회원_테스트() {
        //given
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setName("kim");
        memberB.setName("kim");

        //when
        memberService.join(memberA);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, ()-> {
            memberService.join(memberB);
        });



    }

}