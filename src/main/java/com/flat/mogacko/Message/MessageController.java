package com.flat.mogacko.bot.discord.message;

import com.flat.mogacko.Join.JoinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final JoinService joinService;

    public String getResponse(String message) {
        if (message.startsWith("참여")) {
            return "참여";
        } else {
            return null;
        }
    }
}
