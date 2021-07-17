package com.studty.mogaco.boot.bot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] Args) throws LoginException {

        JDA jda=JDABuilder.createDefault("ODY2MDE2Mzg1MDQyMDg3OTY3.YPManA.WzwBgG4_A0ut6jUkPOCIMKCYQtY").build(); //기본 jda
        jda.addEventListener(new Main()); //jda에 이벤트를 감지하는 리스너를 넣는다.
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContentRaw().equals("!하이")){
            event.getChannel().sendMessage("하이!").queue();
        }
    }

}