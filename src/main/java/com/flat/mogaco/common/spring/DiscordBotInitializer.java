package com.flat.mogacko.common.spring;

import com.flat.mogacko.bot.discord.Transponder;
import com.flat.mogacko.env.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class DiscordBotInitializer implements CommandLineRunner {

    private final List<Transponder> transponderList;

    public DiscordBotInitializer(List<Transponder> transponderList) {
        this.transponderList = transponderList;
    }

    @Override
    public void run(String... args) {
        try {
            String token = Config.getProperty("token");
            JDA jda = JDABuilder.createDefault(token).build();
            transponderList.forEach(obj -> jda.addEventListener(obj));
        }catch (Throwable t){
            log.error(t.getMessage(), t);
            System.exit(1);
        }
    }
}
