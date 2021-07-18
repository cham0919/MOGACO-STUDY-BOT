package com.flat.mogacko.main.common.util;

import com.flat.mogacko.main.common.file.FileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public final class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {}



    /**
     * 대상 파일을 FileInputStream으로 반환한다.<br/>
     *
     * @param file
     *            대상 파일
     * @return 대상 파일에 대한 FileInputStream
     */
    public static FileInputStream openInputStream(File file) {

        if (file.exists()) {

            if (file.isDirectory()) {
                log.error("FileUtils-openInputStream ERROR: 이 존재하나 디렉토리입니다.",file, FileUtils.class);
                return null;
            }

            if (!file.canRead()) {
                log.error("FileUtils-openInputStream ERROR: {}은 읽을수 없습니다.",file,  FileUtils.class);
                return null;
            }

        } else {
            log.error("FileUtils-openInputStream ERROR:  {} 이 존재하지 않습니다." , file , FileUtils.class);
            return null;
        }

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (Exception e) {
            log.error("FileUtils-openInputStream ERROR: {}", e.getMessage(), FileUtils.class);
        }

        return fis;
    }


    public static boolean isPropertiesFile(File file){
//        String extension = FilenameUtils.getExtension(file.getName());
//        return FileExtension.PROPERTIES.equalsIgnoreValue(extension);
        return FileExtension.PROPERTIES.equalsIgnoreValue("");
    }
}
