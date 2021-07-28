package com.flat.mogaco.message;

import com.flat.mogaco.annot.CommandMapping;
import com.flat.mogaco.env.Config;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageController {

    @CommandMapping(command = "명령어")
    public String fetchCommand(){
        return Message.INFO.getMessage();
    }


    @CommandMapping(command = "공지")
    public String getRole(MessageReceivedEvent event, String param){
        if (param != null) {
            if (!event.getMember().getId().equals(event.getGuild().getOwnerId())) {
                return Message.NO_AUTHORITY_INSERT_NOTICE.getMessage();
            }
            Config.setProperty("공지_"+event.getGuild().getId(), param);
            return Message.SUCCESS_INSERT_NOTICE.getMessage();
        } else {
            return Config.getProperty("공지_"+event.getGuild().getId(), Message.NOT_FIND_NOTICE.getMessage());
        }
    }

    @CommandMapping(command = "공지", option = "삭제")
    public String deleteRole(MessageReceivedEvent event){
        if (!event.getMember().getId().equals(event.getGuild().getOwnerId())) {
            return Message.NO_AUTHORITY_DELETE_NOTICE.getMessage();
        }
        Config.setProperty("공지_"+event.getGuild().getId(), Message.NOT_FIND_NOTICE.getMessage());
        return Message.SUCCESS_DELETE_NOTICE.getMessage();
    }
}
