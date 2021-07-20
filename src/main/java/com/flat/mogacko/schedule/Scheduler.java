package com.flat.mogacko.schedule;

import com.flat.mogacko.Join.JoinRecord;
import com.flat.mogacko.Join.JoinRecordService;
import com.flat.mogacko.common.util.TimeUtils;
import com.flat.mogacko.member.Member;
import com.flat.mogacko.member.MemberService;
import com.flat.mogacko.rank.Rank;
import com.flat.mogacko.rank.RankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class Scheduler {

    private final MemberService memberService;
    private final JoinRecordService joinRecordService;
    private final RankService rankService;


    // 매일 00:00시에 오늘 스터디량 더하기
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void initTodayJoinTime() {
        // 오늘 한 것들 조회
        List<Member> memberList = memberService.fetchAllJoinMember();
        memberList.forEach(member -> {
            LocalTime todayJoinTime = member.getTodayJoinTime();

            Rank rank = member.getRank();
            JoinRecord joinRecord = joinRecordService.findOneByMember(member);
            if (joinRecord != null && joinRecord.getLeaveTime() == null) {
                joinRecord.setLeaveTime(LocalDateTime.now());
                LocalTime currentTime = joinRecord.getJoinTime().toLocalTime();
                currentTime = TimeUtils.minusLocalTime(joinRecord.getLeaveTime().toLocalTime(), currentTime);
                todayJoinTime = TimeUtils.plusLocalTime(todayJoinTime, currentTime);
                joinRecordService.voiceJoinStart(member);
            }

            LocalTime totalJoinTime = rank.getTotaljointime();
            totalJoinTime = TimeUtils.plusLocalTime(totalJoinTime, todayJoinTime);
            rank.setTotaljointime(totalJoinTime);
            member.setTodayJoinTime(LocalTime.of(0,0,0));

        });
    }

    // 매주 월요일 00:00에 초기화
    @Transactional
    @Scheduled(cron = "0 0 0 * * MON")
    public void initWeek() {
        List<Member> memberList = memberService.fetchAllJoinMember();
        rankService.initRank();
        joinRecordService.initRecord();
        memberList.forEach(member ->  {
                    member.setTodayJoinTime(LocalTime.of(0,0,0));
                }
        );
    }
}
