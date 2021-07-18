package com.flat.mogacko.main.member;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.naming.TimeLimitExceededException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@Accessors(chain = true)
public class Member {

    public static void main(String[] args) throws InterruptedException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Long a = System.currentTimeMillis();
        Thread.sleep(3000);
        Long b = System.currentTimeMillis();
        Long c = b - a;

        String aa = format.format(new Date(a));
        String bb = format.format(new Date(b));

        long hours = TimeUnit.MILLISECONDS.toHours(c);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(c);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(c);
        System.out.println(aa);
        System.out.println(bb);
        System.out.println("hours: " + hours);
        System.out.println("minutes: " + minutes);
        System.out.println("seconds: " + seconds);
    }

    private String nickName;
    private Long joinTime = 0L;
    private Long leaveTime = 0L;
    private Long joinTotalTime = 0L;


    public static Member of() {
        return new Member();
    }
    private Member() {}

    public Member setLeaveTime(Long leaveTime){
        this.leaveTime = leaveTime;
        return this;
    }

    public Member leaveVoiceChannel() throws TimeLimitExceededException {
        if (joinTime == 0L) { throw new TimeLimitExceededException("참여시간이 기록되어있지 않아요 :("); }
        return setLeaveTime(System.currentTimeMillis())
                .addJoinTotalTime()
                .initTime();
    }

    public Member addJoinTotalTime(){
        Long joinPeriod = leaveTime - joinTime;
        joinTotalTime += joinPeriod;
        return this;
    }

    public String getCurrentJoinTime() throws TimeLimitExceededException {
        if (leaveTime == 0L) {
            leaveVoiceChannel()
                    .setJoinTime(System.currentTimeMillis());
        } else {
            addJoinTotalTime()
                    .initTime();
        }

        return timeToString();
    }

    private String timeToString(){
        long hours = TimeUnit.MILLISECONDS.toHours(joinTotalTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(joinTotalTime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(joinTotalTime);
        return hours+"시간 " + minutes + "분 " + seconds + "초";
    }

    private Member initTime(){
        joinTime = 0L;
        leaveTime = 0L;
        return this;
    }
}
