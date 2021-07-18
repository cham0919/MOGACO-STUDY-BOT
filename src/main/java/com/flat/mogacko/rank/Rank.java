package com.flat.mogacko.rank;


import com.flat.mogacko.MogackoTable.MemberTable;
import com.flat.mogacko.MogackoTable.RankTable;
import com.flat.mogacko.member.Member;

import javax.persistence.*;

@Entity
@Table(name = RankTable.TABLE_NAME)
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = RankTable.PK)
    private Long key;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = MemberTable.PK)
    private Member member;

    @Column(name = RankTable.TOTAL_JOIN_TIME)
    private Long TotalJoinTime;

}
