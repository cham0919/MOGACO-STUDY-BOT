package com.flat.mogacko.bot.discord;

import com.flat.mogacko.Join.JoinRecordService;
import com.flat.mogacko.env.Config;
import com.flat.mogacko.member.MemberDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
@AllArgsConstructor
public class BehaviorTransponder extends Transponder {

    private final JoinRecordService joinRecordService;


    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        // 공지사항 메세지
        event.getGuild().getDefaultChannel().sendMessage(Config.getProperty("공지")).queue();
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        String nickName = event.getMember().getEffectiveName();
        String channel = event.getEntity().getGuild().getName();
        MemberDto memberDto = new MemberDto().setNickName(nickName)
                .setChannel(channel);
        joinRecordService.voiceJoinStart(memberDto);
    } // 보이스 참여

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        String nickName = event.getMember().getEffectiveName();
        String channel = event.getEntity().getGuild().getName();
        MemberDto memberDto = new MemberDto().setNickName(nickName)
                .setChannel(channel);
        joinRecordService.voiceJoinLeave(memberDto);
    } // 보이스 종료
}
