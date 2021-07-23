package com.flat.mogaco.member;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.Join.JoinRecordService;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.rank.Rank;
import com.flat.mogaco.rank.RankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RankRepository rankRepository;
    private final JoinRecordService joinRecordService;


    @Override
    public void joinMember(MemberDto memberDto) {
        Member member = new Member().setChannel(memberDto.getChannel())
                .setNickName(memberDto.getNickName());
        memberRepository.save(member);
        Rank rank = new Rank().setMember(member);
        rankRepository.save(rank);
    }

    @Override
    public List<MemberDto> fetchAllJoinMember(String channel) {
        List<Member> memberList = memberRepository.findAllByChannel(channel);
        List<MemberDto> memberDtoList = new ArrayList<>();
        memberList.forEach(entity -> {
            memberDtoList.add(
                    new MemberDto().setNickName(entity.getNickName())
            );
        });
        return memberDtoList;
    }

    @Override
    public List<Member> fetchAllJoinMember() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public LocalTime fetchTodayJoinTime(MemberDto memberDto) throws NoSuchObjectException {
        Member member = memberRepository.findByChannelAndNickName(memberDto.getChannel(), memberDto.getNickName());
        if (member == null) {
            throw new NoSuchObjectException("참여 인원이 없습니다.");
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
