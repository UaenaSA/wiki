package com.microcore.modules.base;

import com.microcore.jcf.pojo.dto.base.PageParams;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DESC: 关键词模糊查询分页参数
 *
 * @author leizhenyang
 * @date 2018/6/5
 */
public class FuzzyPageParams extends PageParams {

    private boolean isFuzzy;

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = check(keyword);
    }

    public boolean isFuzzy() {
        return isFuzzy;
    }

    public void setFuzzy(boolean fuzzy) {
        isFuzzy = fuzzy;
    }

    public Date dateConvert(String str) {
        Date date = null;
        if (StringUtils.isNotBlank(str)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                date = format.parse(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 检查是否包含% 或 _
     *
     * @param
     * @return
     */
    public String check(String str) {
        if (StringUtils.isEmpty(str)) {
            return"";
        }
        StringBuffer sbf = new StringBuffer();
        for (char c : str.toCharArray()) {
            if (c == '%' || c == '_') {
                sbf.append("\\" + c);
            } else {
                sbf.append(c);
            }
        }
     return  sbf.toString();
    }
}
