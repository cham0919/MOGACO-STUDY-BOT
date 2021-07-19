package com.flat.mogacko.command;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
public enum Command {

    // 인원 관리
    MEMBER_INFO(Arrays.asList("참여", "조회", "랭크", "추방", "탈퇴")),

    // 공지 관리
    ROLE_INFO(Arrays.asList("공지 삭제", "공지")),

    //명령어 찾지 못함
    NONE(Arrays.asList("무슨 말인지 모르겠어요 :("));

    private List<String> commandList;

    public static Command findByMessage(String message) {
        return Arrays.stream(Command.values())
                .filter(command -> command.hasCommand(message))
                .findFirst()
                .orElse(NONE);
    }

    public boolean hasCommand(String message) {
        return commandList.stream()
                .anyMatch(command -> message.startsWith(command));
    }


}
