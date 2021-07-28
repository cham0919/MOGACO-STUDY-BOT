package com.flat.mogaco.member;

import com.flat.mogaco.bot.discord.EventDto;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.List;

public interface MemberService {

    void joinMember(EventDto eventDto);

    List<String> fetchAllJoinMember(MessageReceivedEvent event);

    List<Member> fetchAllJoinMember();

    LocalTime fetchTodayJoinTime(EventDto eventDto) throws NoSuchObjectException;

}
