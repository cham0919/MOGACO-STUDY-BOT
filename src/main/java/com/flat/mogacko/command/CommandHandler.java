package com.flat.mogacko.command;

import com.flat.mogacko.annot.CommandMapping;
import com.flat.mogacko.bot.discord.Transponder;
import com.flat.mogacko.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CommandHandler {

    private Map<Command, Method> commandMethod = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadMogacoMessage(){

        //@CommandMapping이 붙은 Method 탐색
        Reflections reflections =  new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.flat.mogacko")).setScanners(new MethodAnnotationsScanner()));

        Set<Method> methods = reflections.getMethodsAnnotatedWith(CommandMapping.class);

        try {
            methods.forEach(method -> {
                CommandMapping commandMappingAnnotation = method.getAnnotation(CommandMapping.class);
                Command key = commandMappingAnnotation.command();
                commandMethod.put(key, method);
            });
        }  catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

    public Object commandHandle(Map<String, String> chatRoomInfo, String text, Command command){

        // Command 가 실행할 메서드
        Method method = commandMethod.get(command);

        // 실행시킬 메소드가 있는 Controller
        Object controller = BeanUtils.getBean(method.getDeclaringClass());

        // 실행시킬 메소드의 파라미터
        Object[] params = getParams(chatRoomInfo, text, method);

        Object message = null;

        try {
            message = method.invoke(controller, params);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return message;
    }

    // 메소드 파라미터 준비
    private Object[] getParams(Map<String, String> chatRoomInfo, String text, Method method) {

        //
        final Parameter[] parameters = method.getParameters();
        final Class[] parameterTypes = method.getParameterTypes();
        final int parameterCount = method.getParameterCount();

        Object[] params = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {
            Class classOfParam = parameterTypes[i];
            String nameOfParam = parameters[i].getName();

            if (classOfParam == String.class && nameOfParam.equals("text")) {
                params[i] = text;
            }

        }
        return params;
    }
}
