package com.flat.mogaco.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Command {

    Notice("공지", Arrays.asList("삭제"));

    private String command;
    private List<String> option;



}
