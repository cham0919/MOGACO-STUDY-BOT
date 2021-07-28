package com.flat.mogaco.member;

import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.message.Message;
import com.flat.mogaco.rank.Rank;
import com.flat.mogaco.rank.RankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RankRepository rankRepository;


    @Override
    public void joinMember(EventDto eventDto) {
        Member member = new Member().setChannel(eventDto.getChannelId())
                .setUserId(eventDto.getUserId());
        memberRepository.save(member);
        Rank rank = new Rank().setMember(member);
        rankRepository.save(rank);
    }

    @Override
    public List<String> fetchAllJoinMember(MessageReceivedEvent event) {
        List<Member> memberList = memberRepository.findAllByChannel(event.getGuild().getId());
        List<String> nickNameList = memberList.stream()
                .filter(m -> event.getGuild().getMemberById(m.getUserId()) != null)
                .map(m -> event.getGuild().getMemberById(m.getUserId()).getNickname())
                .collect(Collectors.toList());
        return nickNameList;
    }

    @Override
    public List<Member> fetchAllJoinMember() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public LocalTime fetchTodayJoinTime(EventDto eventDto) throws NoSuchObjectException {
        Member member = memberRepository.findByChannelAndUserId(eventDto.getChannelId(), eventDto.getUserId());
        if (member == null) {
            throw new NoSuchObjectException(Message.NO_EXIST_MEMBER.getMessage());
        }
        return member.getTodayJoinTimeUntilNow();
    }
}
