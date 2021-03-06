package com.flat.mogaco.rank;


import com.flat.mogaco.message.Message;
import com.flat.mogaco.annot.CommandMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class RankController {

    private final RankService rankService;
    
    @CommandMapping(command = "랭크")
    public String fetchCurrentRank(MessageReceivedEvent event){
        try {
            return rankService.fetchCurrentRankMessage(event);
        }catch (DataIntegrityViolationException e) {
            return Message.ERROR.getMessage();
        }
    }
}
