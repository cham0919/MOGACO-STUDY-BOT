package com.flat.mogacko.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByChannel(String channel);

    Member findByChannelAndNickName(String channel, String nickName);
}
