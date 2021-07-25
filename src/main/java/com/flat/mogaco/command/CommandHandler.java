package com.flat.mogaco.command;

import com.flat.mogaco.annot.CommandMapping;
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
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CommandHandler {

    private Map<String, Map<String, Method>> commandMethod = new ConcurrentHashMap<>();

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
                String option = "None";
                if (command.contains(" ")) {
                    String[] arrayStr = command.split(" ");
                    command = arrayStr[0];
                    option = arrayStr[1];
                }
                Map<String, Method> optionMap = commandMethod.getOrDefault(command, new ConcurrentHashMap<>());
                log.debug("commandMethod option put :: {}, {}", command, optionMap);
                optionMap.put(option, method);
                commandMethod.put(command, optionMap);
            });
        }  catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

    public Object commandHandle(MessageReceivedEvent event, String message){
        log.debug("CommandHandler :: {}", message);
        Method method = null;
        String command;
        String option;
        String param = null;
        if (message.contains(" ")) {
            command = message.substring(0, message.indexOf(" ")).trim();
            message = message.substring(command.length()).trim();
        } else {
            command = message.trim();
            message = message.substring(command.length()).trim();
        }

        // Command 가 실행할 메서드
        Map<String, Method> optionMap = commandMethod.getOrDefault(command, new HashMap<>());

        if (message.contains(" ")) {
            option = message.substring(0, message.indexOf(" ")).trim();
        } else {
            option = message;
        }

        if (optionMap.containsKey(option)) {
            method = optionMap.get(option);
            param = message.substring(option.length()).trim();
        } else {
            method = optionMap.get("None");
            param = message.length() == 0 ? null : message;
        }

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
        return respMessage;
    }

    // 메소드 파라미터 준비
    private Object[] getParams(MessageReceivedEvent event, String param, Method method) {

        //
        final Parameter[] parameters = method.getParameters();
        final Class[] parameterTypes = method.getParameterTypes();
        final int parameterCount = method.getParameterCount();

        Object[] params = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            Class classOfParam = parameterTypes[i];
            String nameOfParam = parameters[i].getName();

            if (classOfParam == String.class && nameOfParam.equals("param") && param != null) {
                params[i] = param;
            } else if (classOfParam == MessageReceivedEvent.class) {
                params[i] = event;
            }
        }
        return params;
    }
}
