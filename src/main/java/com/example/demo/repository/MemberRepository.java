package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.vo.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {
    // jpa의 쿼리를 커스텀할 때에는 from 후 테이블명이 아니라 entity명을 써줘야한다. entity의 개념을 똑바로 알자. 테이블과 1대1로 매칭되는 객체단위이다.
    // 그럼 jpa는 테이블을 어떻게 알지?
    // entity에게 table매핑을 해주지 않으면, 해당 객체명으로 테이블을 만든다.
    // https://www.icatpark.com/entry/JPA-%EA%B8%B0%EB%B3%B8-Annotation-%EC%A0%95%EB%A6%AC
    @Query("select m from Member m where m.user_id = :user_id and m.user_password = :user_password")
    Member findMember(@Param(value = "user_id") String user_id,
            @Param(value = "user_password") String user_password);
    //
    // @Query("update m from Member m where m.user_id = :user_id and m.user_password =
    // :user_password")
    // Member modifyMember(@Param(value = "user_id") String user_id,
    // @Param(value = "user_password") String user_password,
    // @Param(value = "user_updated_password") String user_updated_password);

    @Query("select m.role from Member m where m.user_id = :user_id")
    String findRole(@Param(value = "user_id") String user_id);
}
