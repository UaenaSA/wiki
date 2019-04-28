package com.microcore.modules.wiki.controller.params;

import com.github.pagehelper.PageInfo;
import com.microcore.modules.wiki.entity.ProblemEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2019/1/17-17:26
 */
@Data
public class ProblemParam {
    private PageInfo<ProblemEntity> pageInfo;
    private ProblemEntity problemEntity;

    private Date submitTimeStart;
    private Date submitTimeEnd;
    private Date lastModifyTimeStart;
    private Date lastModifyTimeEnd;
    private String userName;
}
