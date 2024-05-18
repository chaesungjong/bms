package com.groupd.bms.util;

import java.util.HashMap;

import org.modelmapper.ModelMapper;

import com.groupd.bms.model.Member;

public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Member mapToMember(HashMap<String, Object> map) {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper.map(map, Member.class);
    }
}