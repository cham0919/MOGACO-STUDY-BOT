package com.flat.mogaco.member;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.List;

public interface MemberService {

    void joinMember(MemberDto memberDto);

    List<MemberDto> fetchAllJoinMember(String channel);

    List<Member> fetchAllJoinMember();

    LocalTime fetchTodayJoinTime(MemberDto memberDto) throws NoSuchObjectException;

}
