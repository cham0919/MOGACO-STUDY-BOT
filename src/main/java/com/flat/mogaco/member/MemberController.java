package com.flat.mogaco.member;


import com.flat.mogaco.annot.CommandMapping;
import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MemberController {

    private final MemberService memberService;

    @CommandMapping(command = "참여")
    public String getMemberInfo(EventDto eventDto){
        try {
            memberService.joinMember(eventDto);
            return Message.SUCCSEE_JOIN.getMessage(eventDto.getNickName());
        }catch (DataIntegrityViolationException e) {
            return Message.ALREADY_JOIN.getMessage(eventDto.getNickName());
        }
    }

    @CommandMapping(command = "참여인원")
    public String getAllMemberInfo(EventDto eventDto){
        try {
            List<String> nameList = memberService.fetchAllJoinMember(eventDto.getChannelName());
            if (nameList.size() == 0) {
                    
                return Message.NO_EXIST_MEMBER.getMessage();
            } else {
                return Message.CURRENT_MEMBER.getMessage(nameList);
            }
        }catch (Throwable t) {
            log.error(t.getMessage(), t);
            return Message.ERROR.getMessage();
        }
    }

    @CommandMapping(command = "조회")
    public String getMemberJoinTimeInfo(EventDto eventDto){
        try {
            LocalTime localTime = memberService.fetchTodayJoinTime(eventDto);
            return Message.LOOKUP_JOIN_TIME.getMessage(eventDto.getNickName(), localTime);
        } catch (DataIntegrityViolationException  e) {
            return Message.ERROR.getMessage();
        } catch (NoSuchObjectException e) {
            return Message.NOT_JOIN_MEMBER.getMessage();
        }
    }
}
