package com.flat.mogacko.rank;


import com.flat.mogacko.annot.CommandMapping;
import com.flat.mogacko.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RankController {

    private final RankService rankService;
    
    @CommandMapping(command = "랭크")
    public String fetchCurrentRank(MessageReceivedEvent event){
        try {
            return rankService.fetchCurrentRankMessage(event.getGuild().getName());
        }catch (DataIntegrityViolationException e) {
            return "웁스! 에러 발생 ㅜㅜ";
        }
    }
}
