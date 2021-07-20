package com.flat.mogacko.Join;

import com.flat.mogacko.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRecordRepository extends JpaRepository<JoinRecord, Long> {

    JoinRecord findFirst1ByMemberOrderByKeyDesc(Member member);

    void deleteAllByLeaveTimeNotNull();

}
