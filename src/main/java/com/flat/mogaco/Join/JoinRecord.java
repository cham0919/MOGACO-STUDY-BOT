package com.flat.mogaco.Join;

import com.flat.mogaco.MogackoTable.JoinRecordTable;
import com.flat.mogaco.MogackoTable.MemberTable;
import com.flat.mogaco.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Accessors(chain = true)
@Getter @Setter
@Table(name = JoinRecordTable.TABLE_NAME)
public class JoinRecord {

    public static LocalDateTime serverStartTime = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = JoinRecordTable.PK)
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = MemberTable.PK)
    private Member member;

    @Column(name = JoinRecordTable.JOIN_TIME)
    @CreationTimestamp
    private LocalDateTime joinTime;

    @Column(name = JoinRecordTable.LEAVE_TIME)
    private LocalDateTime leaveTime;


    public LocalTime calcJoinTime(){
        Duration duration = Duration.between(getJoinTime(), getLeaveTime());
        long totalSec = duration.getSeconds();
        int min = (int)totalSec / 60;
        int hour = min / 60;
        int sec = (int)totalSec % 60;
        min = min % 60;
        return LocalTime.of(hour, min, sec);
    }
}
