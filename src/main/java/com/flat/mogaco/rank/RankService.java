package com.flat.mogaco.rank;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface RankService {

    List<Rank> fetchCurrentRank(String channel);

    String fetchCurrentRankMessage(MessageReceivedEvent event);

    void initRank();
}
