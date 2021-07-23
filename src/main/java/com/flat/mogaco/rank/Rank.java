package com.flat.mogaco.rank;


import com.flat.mogaco.MogackoTable.MemberTable;
import com.flat.mogaco.MogackoTable.RankTable;
import com.flat.mogaco.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    private LocalTime totaljointime = LocalTime.of(0,0,0);

    public Rank plusTime(LocalTime localTime){
        totaljointime = totaljointime.plusHours(localTime.getHour())
                .plusMinutes(localTime.getMinute())
                .plusSeconds(localTime.getSecond());
        return this;
    }

    public String getTimeFormat(){
        return totaljointime.getHour() + "시 " + totaljointime.getMinute() + "분 " + totaljointime.getSecond() + "초";
    }
}
