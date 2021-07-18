package com.flat.mogacko.JoinRecord;

import com.flat.mogacko.MogackoTable.JoinRecordTable;
import com.flat.mogacko.MogackoTable.MemberTable;
import com.flat.mogacko.member.Member;

import javax.persistence.*;

@Entity
@Table(name = JoinRecordTable.TABLE_NAME)
public class JoinRecord {

    @Id
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = MemberTable.PK)
    private Member memberList;

}
