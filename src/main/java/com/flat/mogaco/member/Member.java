package com.flat.mogaco.member;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.MogackoTable.MemberTable;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.rank.Rank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Data
@Accessors(chain = true)
@Entity
@Table(name = MemberTable.TABLE_NAME,
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={MemberTable.CHANNEL, MemberTable.USERID}
                )
        })
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MemberTable.PK)
    private Long key;

    @Column(name = MemberTable.CHANNEL)
    private String channel;

    @Column(name = MemberTable.USERID)
    private String userId;

    @Column(name = MemberTable.TODAYJOINTIME)
    private LocalTime todayJoinTime = LocalTime.of(0,0,0);

    @Column(name = MemberTable.JOINDATE)
    @CreationTimestamp
    private LocalDateTime joinDate;

    @OneToOne(mappedBy = "member")
    private Rank rank;

    @OneToMany(mappedBy = "member")
    private List<JoinRecord> joinRecordList = new ArrayList<>();

    public Member addJoinTime(LocalTime localTime){
        todayJoinTime = TimeUtils.plusLocalTime(todayJoinTime, localTime);
        return this;
    }

    public LocalTime getTodayJoinTimeUntilNow() {
        LocalTime todayJoinTimeUntilNow = todayJoinTime;
        if (joinRecordList.size() == 0) return todayJoinTimeUntilNow;

        joinRecordList.sort(Comparator.comparing(JoinRecord::getJoinTime, Collections.reverseOrder()));
        JoinRecord joinRecord = joinRecordList.get(0);

        if (joinRecord != null && joinRecord.getLeaveTime() == null) {
            LocalTime currentTime = joinRecord.getJoinTime().toLocalTime();
            currentTime = TimeUtils.minusLocalTime(LocalTime.now(), currentTime);
            todayJoinTimeUntilNow = TimeUtils.plusLocalTime(todayJoinTimeUntilNow, currentTime);
        }

        return todayJoinTimeUntilNow;
    }
}
