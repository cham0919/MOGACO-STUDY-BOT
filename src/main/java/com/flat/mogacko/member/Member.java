package com.flat.mogacko.member;

import com.flat.mogacko.JoinRecord.JoinRecord;
import com.flat.mogacko.MogackoTable.MemberTable;
import com.flat.mogacko.rank.Rank;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Accessors(chain = true)
@Entity
@Table(name = MemberTable.TABLE_NAME)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MemberTable.PK)
    private Long key;

    @Column(name = MemberTable.NICKNAME)
    private String nickName;

    @Column(name = MemberTable.JOINDATE)
    private Long joinDate;

    @OneToOne(mappedBy = "member")
    private Rank rank;

    @OneToMany(mappedBy = "member")
    private List<JoinRecord> joinRecordList = new ArrayList<>();

//    public static void main(String[] args) throws InterruptedException {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Long a = System.currentTimeMillis();
//        Thread.sleep(3000);
//        Long b = System.currentTimeMillis();
//        Long c = b - a;
//
//        String aa = format.format(new Date(a));
//        String bb = format.format(new Date(b));
//
//        long hours = TimeUnit.MILLISECONDS.toHours(c);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(c);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(c);
//        System.out.println(aa);
//        System.out.println(bb);
//        System.out.println("hours: " + hours);
//        System.out.println("minutes: " + minutes);
//        System.out.println("seconds: " + seconds);
//    }


//
//
//    public Member setLeaveTime(Long leaveTime){
//        this.leaveTime = leaveTime;
//        return this;
//    }
//
//    public Member leaveVoiceChannel() throws TimeLimitExceededException {
//        if (joinTime == 0L) { throw new TimeLimitExceededException("참여시간이 기록되어있지 않아요 :("); }
//        return setLeaveTime(System.currentTimeMillis())
//                .addJoinTotalTime()
//                .initTime();
//    }
//
//    public Member addJoinTotalTime(){
//        Long joinPeriod = leaveTime - joinTime;
//        joinTotalTime += joinPeriod;
//        return this;
//    }
//
//    public String getCurrentJoinTime() throws TimeLimitExceededException {
//        if (leaveTime == 0L) {
//            leaveVoiceChannel()
//                    .setJoinTime(System.currentTimeMillis());
//        } else {
//            addJoinTotalTime()
//                    .initTime();
//        }
//
//        return timeToString();
//    }
//
//    private String timeToString(){
//        long hours = TimeUnit.MILLISECONDS.toHours(joinTotalTime);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(joinTotalTime);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(joinTotalTime);
//        return hours+"시간 " + minutes + "분 " + seconds + "초";
//    }
//
//    private Member initTime(){
//        joinTime = 0L;
//        leaveTime = 0L;
//        return this;
//    }
}
