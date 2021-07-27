package com.flat.mogaco.env;

import com.flat.mogaco.common.util.BeanUtils;
import com.flat.mogaco.common.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Properties;

public class PropertiesEnvironment implements Environment {

    private final Logger log = LoggerFactory.getLogger(PropertiesEnvironment.class);

    private Properties props = new Properties();


    private static PropertiesEnvironment instance;

    private PropertiesEnvironment() {

    }

    public static PropertiesEnvironment getInstance(){
        if (instance == null) {
            synchronized (PropertiesEnvironment.class) {
                if (instance == null) {
                    instance = new PropertiesEnvironment();
                }
            }
        }
        return instance;
    }

    private void init()  {
        try {
            File[] propFiles = fetchPropertiesFiles();
            Arrays.stream(propFiles)
                    .forEach(this::loadProp);
        }catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void loadProp(File propFile){
        try (
                FileInputStream is = FileUtils.openInputStream(propFile);
        ){
            props.load(new InputStreamReader(is, Charset.forName("UTF-8")));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private File[] fetchPropertiesFiles() throws IOException {
        File propDir = ResourceUtils.getFile("classpath:command");
        return propDir.listFiles(FileUtils::isPropertiesFile);
    }


    public void addProperties(Properties properties) {
        if (properties == null || properties.isEmpty()) { return; }
        log.trace("Adding Properties: {}", properties);
        props.putAll(properties);
    }


    @Override
    public Properties getAllProperties() {
        return props;
    }


}
