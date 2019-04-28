package com.microcore.modules;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 当Spring加载完成后执行的类
 *
 * @author Leizhenyang
 * @date 2017/12/15
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

    }
}
