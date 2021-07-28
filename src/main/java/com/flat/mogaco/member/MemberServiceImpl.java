package com.flat.mogaco.member;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.Join.JoinRecordService;
import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.message.Message;
import com.flat.mogaco.rank.Rank;
import com.flat.mogaco.rank.RankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JoinRecordService joinRecordService;


    @Override
    public void joinMember(EventDto eventDto) {
        Member member = new Member().setChannel(eventDto.getChannelName())
                .setNickName(eventDto.getNickName());
        memberRepository.save(member);
        Rank rank = new Rank().setMember(member);
        rankRepository.save(rank);
    }

    @Override
    public List<String> fetchAllJoinMember(String channel) {
        List<Member> memberList = memberRepository.findAllByChannel(channel);
        List<String> nickNameList = memberList.stream().map(m -> m.getNickName()).collect(Collectors.toList());
        return nickNameList;
    }

    @Override
    public List<Member> fetchAllJoinMember() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public LocalTime fetchTodayJoinTime(EventDto eventDto) throws NoSuchObjectException {
        Member member = memberRepository.findByChannelAndNickName(eventDto.getChannelName(), eventDto.getNickName());
        if (member == null) {
            throw new NoSuchObjectException(Message.NO_EXIST_MEMBER.getMessage());
        }
        LocalTime todayJoinTime = member.getTodayJoinTime();
        JoinRecord joinRecord = joinRecordService.findOneByMember(member);
        if (joinRecord != null && joinRecord.getLeaveTime() == null) {
            LocalTime currentTime = joinRecord.getJoinTime().toLocalTime();
            currentTime = TimeUtils.minusLocalTime(LocalTime.now(), currentTime);
            todayJoinTime = TimeUtils.plusLocalTime(todayJoinTime, currentTime);
        }

        return todayJoinTime;
    }
}
