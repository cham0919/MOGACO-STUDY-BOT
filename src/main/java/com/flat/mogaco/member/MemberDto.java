package com.flat.mogacko.member;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MemberDto {

    private String channel;
    private String nickName;
}