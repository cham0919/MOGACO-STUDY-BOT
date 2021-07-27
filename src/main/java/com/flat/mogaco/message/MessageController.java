package com.flat.mogaco.message;

import com.flat.mogaco.annot.CommandMapping;
import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.env.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageController {

    @CommandMapping(command = "명령어")
    public String fetchCommand(){
        return Message.INFO.getMessage();
    }


    @CommandMapping(command = "공지")
    public String getRole(EventDto eventDto, String param){
        if (param == null) {
            return Config.getProperty("공지_"+eventDto.getChannelId(), Message.NOT_FIND_NOTICE.getMessage());
        } else {
            Config.setProperty("공지_"+eventDto.getChannelId(), param);
            return Message.SUCCESS_DELETE_NOTICE.getMessage();
        }
    }

    @CommandMapping(command = "공지", option = "삭제")
    public String deleteRole(EventDto eventDto){
        Config.setProperty("공지_"+eventDto.getChannelId(), Message.NOT_FIND_NOTICE.getMessage());
        return Message.SUCCESS_DELETE_NOTICE.getMessage();
    }
}
