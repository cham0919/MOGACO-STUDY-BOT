package com.flat.mogacko.bot.discord;

import com.flat.mogacko.bot.discord.message.MessageService;
import com.flat.mogacko.command.Command;
import com.flat.mogacko.command.CommandHandler;
import com.flat.mogacko.env.Config;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MessageTransponder extends Transponder{

    private final MessageService messageService;
    private final CommandHandler commandHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        if (message.startsWith("!")) {
            message = message.substring(1);
            Command command = Command.findByMessage(message);
            String respMessage = (String)commandHandler.commandHandle(null, message, command);
            if (respMessage != null) {
                event.getChannel().sendMessage(respMessage).queue();
            }
        }
    }

    public String fetchResponseMessage(String message) {
        String respMessage = Config.getProperty(message.substring(1));
        if (respMessage != null) {
            return respMessage;
        } else {
            return messageService.getResponse(message);
        }
    }
}
