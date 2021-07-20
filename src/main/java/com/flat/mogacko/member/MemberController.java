package com.flat.mogacko.member;


import com.flat.mogacko.annot.CommandMapping;
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
    public String getMemberInfo(MessageReceivedEvent event){
        MemberDto dto = new MemberDto()
                .setChannel(event.getGuild().getName())
                .setNickName(event.getMember().getEffectiveName());
        try {
            memberService.joinMember(dto);
            return dto.getNickName() + "님이 스터디에 참여하셨습니다!";
        }catch (DataIntegrityViolationException e) {
            return dto.getNickName()+"님은 이미 참여 중입니다!";
        }
    }

    @CommandMapping(command = "참여인원")
    public String getAllMemberInfo(MessageReceivedEvent event){
        try {
            List<MemberDto> memberDtoList = memberService.fetchAllJoinMember(event.getGuild().getName());
            StringBuilder respMessage =  new StringBuilder();
            if (memberDtoList.size() == 0) {
                respMessage.append("현재 참여 인원이 없습니다");
            } else {
                respMessage.append("현재 참여 인원입니다 \n");
                for (int i = 1; i <= memberDtoList.size(); i++) {
                    respMessage.append(i + ". " + memberDtoList.get(i-1).getNickName() + "\n");
                }
            }
            return respMessage.toString();
        }catch (Throwable t) {
            log.error(t.getMessage(), t);
            return "웁스! 에러 발생 ㅜㅜ";
        }
    }

    @CommandMapping(command = "조회")
    public String getMemberJoinTimeInfo(MessageReceivedEvent event){
        MemberDto dto = new MemberDto()
                .setChannel(event.getGuild().getName())
                .setNickName(event.getMember().getEffectiveName());
        try {
            LocalTime localTime = memberService.fetchTodayJoinTime(dto);
            return dto.getNickName() + "님은 오늘  " + localTime.getHour() + "시간 "+
                    localTime.getMinute()+"분"+
                    localTime.getSecond()+"초 공부하셨습니다";
        } catch (DataIntegrityViolationException  e) {
            return "웁스! 에러 발생 ㅜㅜ";
        } catch (NoSuchObjectException e) {
            return "참여 중이 아닙니다!";
        }
    }
}
