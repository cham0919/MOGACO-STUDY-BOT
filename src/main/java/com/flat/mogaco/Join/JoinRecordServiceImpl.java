package com.flat.mogaco.Join;

import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.member.Member;
import com.flat.mogaco.member.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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

        // joinRecord가 없거나 joinRecord 나간 기록이 있을 때
        // 즉, 서버가 죽었을 때, 보이스 챗에 들어와있었을 경우
        if (joinRecord == null || joinRecord.getLeaveTime() != null) {
            DayOfWeek dayOfWeek = JoinRecord.serverStartTime.getDayOfWeek();

            // 서버 기동 시간이 저번주였을 경우
            if (dayOfWeek.getValue() > LocalDateTime.now().getDayOfWeek().getValue()) {
                // 서버 기동 시간을 지금으로 변경
                JoinRecord.serverStartTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0,0,0));
            }

            joinRecord = new JoinRecord().setMember(member)
                    .setJoinTime(JoinRecord.serverStartTime)
                    .setLeaveTime(LocalDateTime.now());
            joinRecordRepository.save(joinRecord);
        } else {
            joinRecord.setLeaveTime(LocalDateTime.now());
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
        return memberRepository.findByChannelAndUserId(eventDto.getChannelId(), eventDto.getUserId());
    }
}
