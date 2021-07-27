package com.flat.mogaco.member;


import com.flat.mogaco.annot.CommandMapping;
import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            return eventDto.getNickName() + "님이 스터디에 참여하셨습니다!";
        }catch (DataIntegrityViolationException e) {
            return eventDto.getNickName()+"님은 이미 참여 중입니다!";
        }
    }

    @CommandMapping(command = "참여인원")
    public String getAllMemberInfo(MessageReceivedEvent event){
        try {
            List<String> nameList = memberService.fetchAllJoinMember(event.getGuild().getName());
            StringBuilder respMessage =  new StringBuilder();
            if (nameList.size() == 0) {
                respMessage.append(Message.NO_EXIST_MEMBER.getMessage());
            } else {
                respMessage.append(Message.CURRENT_MEMBER.getMessage() + "\n");
                for (int i = 1; i <= nameList.size(); i++) {
                    respMessage.append(i + ". " + nameList.get(i-1) + "\n");
                }
            }
            return respMessage.toString();
        }catch (Throwable t) {
            log.error(t.getMessage(), t);
            return Message.ERROR.getMessage();
        }
    }

    @CommandMapping(command = "조회")
    public String getMemberJoinTimeInfo(EventDto eventDto){
        try {
            LocalTime localTime = memberService.fetchTodayJoinTime(eventDto);
            return eventDto.getNickName() + "님은 오늘  " + localTime.getHour() + "시간 "+
                    localTime.getMinute()+"분"+
                    localTime.getSecond()+"초 공부하셨습니다";
        } catch (DataIntegrityViolationException  e) {
            return Message.ERROR.getMessage();
        } catch (NoSuchObjectException e) {
            return Message.NOT_JOIN_MEMBER.getMessage();
        }
    }
}
