package com.flat.mogacko.bot.discord;

import com.flat.mogacko.env.Config;
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

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        // 공지사항 메세지
        event.getGuild().getDefaultChannel().sendMessage(Config.getProperty("role")).queue();
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        String nickName = event.getMember().getEffectiveName();
    } // 보이스 참여

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        String nickName = event.getMember().getEffectiveName();
    } // 보이스 종료
}
