package com.flat.mogacko.Join;

import com.flat.mogacko.MogackoTable.JoinRecordTable;
import com.flat.mogacko.MogackoTable.MemberTable;
import com.flat.mogacko.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = JoinRecordTable.TABLE_NAME)
public class JoinRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = JoinRecordTable.PK)
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = MemberTable.PK)
    private Member member;

    @Column(name = JoinRecordTable.JOIN_TIME)
    private LocalDateTime joinTime;

    @Column(name = JoinRecordTable.LEAVE_TIME)
    private LocalDateTime leaveTime;

}
