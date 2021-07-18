package com.flat.mogacko.main;

import com.flat.mogacko.main.env.Config;
import com.flat.mogacko.main.member.Member;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.naming.TimeLimitExceededException;
import javax.security.auth.login.LoginException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends ListenerAdapter {

    Map<String, Member> memberList = new ConcurrentHashMap();

    public static void main(String[] Args) throws LoginException {

        JDA jda=JDABuilder.createDefault("ODY2MDE2Mzg1MDQyMDg3OTY3.YPManA.m1sz2IXepEADmGbsR8H-tdOVAVE").build(); //기본 jda
        jda.addEventListener(new Main()); //jda에 이벤트를 감지하는 리스너를 넣는다.
    }


    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        String nickName = event.getMember().getEffectiveName();

        Member member = memberList.get(nickName);
        member.setJoinTime(System.currentTimeMillis());
    } // 보이스 참여
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        String nickName = event.getMember().getEffectiveName();
        Member member = memberList.get(nickName);
        member.setLeaveTime(System.currentTimeMillis());
    } // 보이스 종료

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentRaw();
        if (message.startsWith("!")) {
            if (message.startsWith("!참여시간")){
                String nickname = event.getMember().getEffectiveName();
                String respMsg = "";
                try {
                    respMsg = memberList.get(nickname).getCurrentJoinTime();
                }catch (TimeLimitExceededException e) {
                    respMsg = e.getMessage();
                }
                event.getChannel().sendMessage(respMsg).queue();
            } else if (message.startsWith("!참여")){
                String nickname = event.getMember().getEffectiveName();
                if (memberList.containsKey(nickname)) {
                    event.getChannel().sendMessage("이미 참여 중이예요").queue();
                } else {
                    Member member = Member.of().setNickName(nickname);
                    memberList.put(member.getNickName(), member);
                    event.getChannel().sendMessage("참여되었습니다 :)").queue();
                }
            } else {
                String respMessage = Config.getProperty(message.substring(1), "어떤 명령어인지 잘 모르겠어요");
                event.getChannel().sendMessage(respMessage).queue();
            }
        }
    }



    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        // 공지사항 메세지
        event.getGuild().getDefaultChannel().sendMessage( Config.getProperty("role")).queue();
    }
}
