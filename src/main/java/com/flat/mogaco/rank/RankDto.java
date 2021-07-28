package com.flat.mogaco.rank;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

@Getter @Setter
@Accessors(chain = true)
public class RankDto implements Comparable<RankDto> {

    private String nickName;
    private Duration totalTime;


    @Override
    public int compareTo(@NotNull RankDto o) {
        long result =  this.totalTime.toMillis() - o.getTotalTime().toMillis();
        return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
}
