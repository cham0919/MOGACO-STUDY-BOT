package com.flat.mogaco.Join;

import com.flat.mogaco.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRecordRepository extends JpaRepository<JoinRecord, Long> {

    JoinRecord findFirst1ByMemberOrderByKeyDesc(Member member);

    void deleteAllByLeaveTimeNotNull();

}
