package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import org.apache.commons.lang3.StringUtils;

/**
 * 路径校验
 *
 * @author leizhenyang
 */
public class Path extends AbstractRule<String>
{
    /**
     * 文件路径匹配.首先不能包含特殊字符 * | \ / ? < > "
     * 需要匹配绝对路径和相对路径还有linux这样的路径名
     */
    //private static final String PATH = "[^(\\*|\\?|\\:|\"|\\>|\\<|\\|)]{1,10}";
    private static final String PATH = "(([a-zA-Z][:])|(\\.{1,2}))?([/|\\\\][^(\\*|\\?|\\:|\"|\\>|\\<|\\|)]{1,}){1,}";
    @Override
    public boolean valid(String value)
    {
        if(StringUtils.isBlank(value)){
            return true;
        }
        if(!value.matches(PATH)){
            message= "路径不合法";
            return false;
        }
        return true;
    }
}
