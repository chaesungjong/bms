package com.groupd.bms.util;

import java.util.HashMap;

import org.modelmapper.ModelMapper;

import com.groupd.bms.model.Member;

/**
 * MapperUtil
 * ModelMapper를 사용하여 HashMap을 Member로 변환하는 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.MapperUtil
 */
public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Member mapToMember(HashMap<String, Object> map) {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper.map(map, Member.class);
    }
}