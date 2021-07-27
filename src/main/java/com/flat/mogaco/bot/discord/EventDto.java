package com.flat.mogaco.bot.discord;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Getter @Setter
@Accessors(chain = true)
public class EventDto {

    private String channelId;
    private String channelName;
    private String nickName;

    public EventDto(MessageReceivedEvent event) {
        this.channelId = event.getGuild().getId();
        this.channelName = event.getGuild().getName();
        this.nickName = event.getMember().getEffectiveName();
    }

    public EventDto(GenericGuildVoiceUpdateEvent event) {
        this.channelId = event.getGuild().getId();
        this.channelName = event.getGuild().getName();
        this.nickName = event.getMember().getEffectiveName();
    }
}
