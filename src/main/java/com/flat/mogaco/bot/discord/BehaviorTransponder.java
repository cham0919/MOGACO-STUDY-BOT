package com.flat.mogaco.bot.discord;

import com.flat.mogaco.Join.JoinRecordService;
import com.flat.mogaco.env.Config;
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
        event.getGuild().getDefaultChannel().sendMessage(Config.getProperty("공지_"+event.getGuild().getId())).queue();
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        EventDto eventDto = new EventDto(event);
        joinRecordService.voiceJoinStart(eventDto);
    } // 보이스 참여

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        EventDto eventDto = new EventDto(event);
        joinRecordService.voiceJoinLeave(eventDto);
    } // 보이스 종료
}
