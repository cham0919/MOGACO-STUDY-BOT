package com.flat.mogaco.rank;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter @Setter
@Accessors(chain = true)
public class RankDto {

    private String nickName;
    private LocalTime totalTime;
}
