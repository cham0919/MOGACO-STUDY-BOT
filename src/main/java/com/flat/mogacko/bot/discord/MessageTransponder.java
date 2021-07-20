package com.flat.mogacko.bot.discord;

import com.flat.mogacko.command.CommandHandler;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MessageTransponder extends Transponder{

    private final CommandHandler commandHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        if (message.startsWith("!")) {
            String respMessage = (String)commandHandler.commandHandle(event, message.substring(1).trim());
            if (respMessage != null) {
                event.getChannel().sendMessage(respMessage).queue();
            }
        }
    }
}
