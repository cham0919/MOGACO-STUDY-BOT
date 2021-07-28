package com.flat.mogaco.rank;


import com.flat.mogaco.MogackoTable.MemberTable;
import com.flat.mogaco.MogackoTable.RankTable;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Getter @Setter
@Accessors(chain = true)
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
    private Duration totaljointime = Duration.ZERO;

    public Duration getTotalJoinTimeUntilNow() {
        LocalTime todayJoinTime = member.getTodayJoinTimeUntilNow();
        Duration totalTime = TimeUtils.plusTime(totaljointime, todayJoinTime);
        return totalTime;
    }
}
