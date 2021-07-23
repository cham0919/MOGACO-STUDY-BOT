package com.flat.mogaco.Join;

import com.flat.mogaco.member.Member;
import com.flat.mogaco.member.MemberDto;

public interface JoinRecordService {

    // 참여 시작
    void voiceJoinStart(MemberDto memberDto);
    void voiceJoinStart(Member member);

    // 참여 종료
    void voiceJoinLeave(MemberDto memberDto);


    // 기록 최근꺼 가져오기
    JoinRecord findOneByMember(Member member);


    void initRecord();

}
