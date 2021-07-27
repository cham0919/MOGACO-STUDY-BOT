package com.flat.mogaco.Join;

import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.member.Member;
import com.flat.mogaco.member.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class JoinRecordServiceImpl implements JoinRecordService {

    private final JoinRecordRepository joinRecordRepository;
    private final MemberRepository memberRepository;

    @Override
    public void voiceJoinStart(EventDto eventDto) {
        Member member = findByChannelAndNickName(eventDto);
        if (member != null) {
            voiceJoinStart(member);
        }
    }

    @Override
    public void voiceJoinStart(Member member) {
        JoinRecord joinRecord = new JoinRecord()
                .setMember(member);
        joinRecordRepository.save(joinRecord);
    }


    @Override
    @Transactional
    public void voiceJoinLeave(EventDto eventDto) {
        Member member = findByChannelAndNickName(eventDto);
        JoinRecord joinRecord = findOneByMember(member);
        if (joinRecord == null || joinRecord.getLeaveTime() != null) {
            DayOfWeek dayOfWeek = JoinRecord.serverStartTime.getDayOfWeek();
            if (dayOfWeek.getValue() > LocalDateTime.now().getDayOfWeek().getValue()) {
                JoinRecord.serverStartTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0,0,0));
            }

            joinRecord = new JoinRecord().setMember(member);
            joinRecordRepository.save(joinRecord);
            joinRecord.setJoinTime(JoinRecord.serverStartTime)
                    .setLeaveTime(LocalDateTime.now());
        } else {
            joinRecord.setLeaveTime(LocalDateTime.now());
            joinRecordRepository.save(joinRecord);
        }

        LocalTime localTime = TimeUtils.minusLocalTime(joinRecord.getLeaveTime().toLocalTime(), joinRecord.getJoinTime().toLocalTime());
        member.addJoinTime(localTime);
    }

    @Override
    public JoinRecord findOneByMember(Member member) {
        return joinRecordRepository.findFirst1ByMemberOrderByKeyDesc(member);
    }

    @Override
    public void initRecord() {
        joinRecordRepository.deleteAllByLeaveTimeNotNull();
        List<JoinRecord> joinRecordList = joinRecordRepository.findAll();
        joinRecordList.forEach(joinRecord -> {
            joinRecord.setJoinTime(LocalDateTime.now())
                    .setLeaveTime(null);
        });
    }

    private Member findByChannelAndNickName(EventDto eventDto) {
        return memberRepository.findByChannelAndNickName(eventDto.getChannelName(), eventDto.getNickName());
    }
}
