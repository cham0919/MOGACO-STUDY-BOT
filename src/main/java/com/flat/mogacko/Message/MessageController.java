package com.flat.mogacko.Message;

import com.flat.mogacko.annot.CommandMapping;
import com.flat.mogacko.env.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageController {

    @CommandMapping(command = "공지")
    public String getRole(String param){
        if (param == null) {
            return Config.getProperty("공지");
        } else {
            Config.setProperty("공지", param);
            return "공지가 등록되었습니다.";
        }
    }

    @CommandMapping(command = "공지 삭제")
    public String deleteRole(){
        Config.setProperty("공지", "등록된 공지가 없습니다 :(");
        return "공지가 삭제되었습니다";
    }
}