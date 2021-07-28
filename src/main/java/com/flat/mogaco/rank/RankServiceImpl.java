package com.flat.mogaco.rank;

import com.flat.mogaco.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;

    @Override
    @Transactional
    public String fetchCurrentRankMessage(MessageReceivedEvent event) {
        List<Rank> rankList = fetchCurrentRank(event.getGuild().getId());
        List<RankDto> rankDtos = new ArrayList<>();

        for (Rank rank : rankList) {
            Member member = event.getGuild().getMemberById(rank.getMember().getUserId());
            if (member == null) continue;
            String nickName = member.getNickname();
            Duration duration = rank.getTotalJoinTimeUntilNow();
            RankDto dto = new RankDto()
                    .setNickName(nickName)
                    .setTotalTime(duration);
            rankDtos.add(dto);
        }

        rankDtos.sort(Collections.reverseOrder());

        if (rankList.size() > 0) {
            return Message.CURRENT_RANK.getMessage(rankDtos);
        } else {
            return Message.NO_EXIST_MEMBER.getMessage();
        }
    }

    @Override
    public void initRank() {
        List<Rank> rankList = rankRepository.findAll();
        rankList.forEach(rank -> {
            rank.setTotaljointime(Duration.ZERO);
        });
    }

    @Override
    public List<Rank> fetchCurrentRank(String channel) {
        return rankRepository.findAllByMember_Channel(channel);
    }
}
