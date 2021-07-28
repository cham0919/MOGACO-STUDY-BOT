package com.flat.mogaco.command;

import com.flat.mogaco.annot.CommandMapping;
import com.flat.mogaco.bot.discord.EventDto;
import com.flat.mogaco.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Slf4j
@Service
public class CommandHandler {

    private Map<String, Map<String, Method>> commandMethod = new HashMap<>();

    @PostConstruct
    public void loadMogacoMessage(){

        //@CommandMapping이 붙은 Method 탐색
        Reflections reflections =  new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.flat.mogaco")).setScanners(new MethodAnnotationsScanner()));

        Set<Method> methods = reflections.getMethodsAnnotatedWith(CommandMapping.class);

        try {
            methods.forEach(method -> {
                CommandMapping commandMappingAnnotation = method.getAnnotation(CommandMapping.class);
                String command = commandMappingAnnotation.command();
                String option = commandMappingAnnotation.option();
                Map<String, Method> optionMap = commandMethod.getOrDefault(command, new HashMap<>());
                log.debug("commandMethod option put :: {}, {}", command, optionMap);
                optionMap.put(option, method);
                commandMethod.put(command, optionMap);
            });
        }  catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

    private boolean isOption(Set<String> optionSet, String option) {
        return optionSet.stream().anyMatch(o -> o.equals(option));
    }

    public String commandHandle(MessageReceivedEvent event, String message){
        Method method = null;
        String command = null;
        String option = "Default";
        String param = null;

        String[] messageArray = message.split(" ");
        command = messageArray[0];

        if (!commandMethod.containsKey(command)) { return null; }

        Map<String, Method> optionMap = commandMethod.get(command);
        Set<String> optionSet = optionMap.keySet();
        if (messageArray.length > 1) {

            if (messageArray.length == 2 && isOption(optionSet, messageArray[1])) {
                option = messageArray[1];
            } else {
                param = "";
                for (int i = 1; i < messageArray.length; i++) {
                    param += messageArray[i];
                }
            }
        }

        method = optionMap.get(option);

        // 실행시킬 메소드가 있는 Controller
        Object controller = BeanUtils.getBean(method.getDeclaringClass());

        // 실행시킬 메소드의 파라미터
        Object[] params = getParams(event, param, method);

        Object respMessage = null;

        try {
            respMessage = method.invoke(controller, params);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return (String)respMessage;
    }

    // 메소드 파라미터 준비
    private Object[] getParams(MessageReceivedEvent event, String param, Method method) {
        EventDto eventDto = new EventDto(event);

        final Parameter[] parameters = method.getParameters();
        final Class[] parameterTypes = method.getParameterTypes();
        final int parameterCount = method.getParameterCount();

        Object[] params = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            Class classOfParam = parameterTypes[i];
            String nameOfParam = parameters[i].getName();

            if (classOfParam == String.class && nameOfParam.equals("param") && param != null) {
                params[i] = param;
            } else if (classOfParam == EventDto.class) {
                params[i] = eventDto;
            } else if (classOfParam == MessageReceivedEvent.class) {
                params[i] = event;
            }
        }
        return params;
    }
}
