package com.flat.mogaco.Join;

import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.member.Member;

public interface JoinRecordService {

    // 참여 시작
    void voiceJoinStart(EventDto eventDto);
    void voiceJoinStart(Member member);

    // 참여 종료
    void voiceJoinLeave(EventDto eventDto);


    // 기록 최근꺼 가져오기
    JoinRecord findOneByMember(Member member);


    void initRecord();

}
