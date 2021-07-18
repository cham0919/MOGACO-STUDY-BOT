package com.flat.mogacko.main.common.file;

import com.flat.mogacko.common.lang.BaseEnum;
import lombok.Getter;

@Getter
public enum FileExtension implements BaseEnum {

    PROPERTIES("properties");

    private String fileExtension;


    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean equalsValue(String value){
        return value.equals(getFileExtension());
    }

    @Override
    public boolean equalsIgnoreValue(String value){
        return value.equalsIgnoreCase(getFileExtension());
    }
}
