package com.flat.mogaco.member;

import com.flat.mogaco.bot.discord.EventDto;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.List;

public interface MemberService {

    void joinMember(EventDto eventDto);

    List<String> fetchAllJoinMember(String channel);

    List<Member> fetchAllJoinMember();

    LocalTime fetchTodayJoinTime(EventDto eventDto) throws NoSuchObjectException;

}
