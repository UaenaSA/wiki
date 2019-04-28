package com.microcore.config;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * DESC: 日期类格式化
 *
 * @author leizhenyang
 * @date 2018/5/31
 */
public class MVCDateFormat extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(new Date(text));
        }
    }
}
