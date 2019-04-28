package com.microcore.modules.wiki.controller.params;

import com.github.pagehelper.PageInfo;
import com.microcore.modules.wiki.entity.LogEntity;
import lombok.Data;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2019/1/17-17:24
 */
@Data
public class LogParam {
    private PageInfo<LogEntity> pageInfo;
    private LogEntity logEntity;
}
