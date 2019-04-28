package com.microcore;

import com.microcore.jcf.config.Global;
import com.microcore.modules.sys.controller.SysLoginController;
import com.microcore.modules.sys.controller.params.LoginParams;
import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * DESC:单元测试Controller基类
 *
 * @author leizhenyang
 * @date 2018/5/27
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public abstract class BaseControllerTest {

    /**
     * 用户账号
     *
     * @return
     */
    protected abstract String getUserName();

    /**
     * 用户密码
     *
     * @return
     */
    protected abstract String getPassword();

    private final String captcha = "test";

    @Autowired
    private SysLoginController sysLoginService;

    @Autowired
    private org.apache.shiro.mgt.SecurityManager securityManager;

    /**
     * 模拟登陆
     *
     * @throws IOException
     */
    @Before
    public void setup() throws IOException {
        Global.isJunitTest = true;
        SecurityUtils.setSecurityManager(securityManager);
        LoginParams params = new LoginParams();
        params.setUsername(getUserName());
        params.setPassword(getPassword());
        params.setCaptcha(captcha);
        sysLoginService.login(params);
    }

}
