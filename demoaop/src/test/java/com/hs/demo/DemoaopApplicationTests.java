package com.hs.demo;

import com.hs.demo.anno.SysLog;
import com.hs.demo.dao.UserRepository;
import com.hs.demo.pojo.SysUser;
import com.hs.demo.proxy.CGlibService;
import com.hs.demo.service.IndexService;
import com.hs.demo.service.impl.IndexServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@Slf4j
class DemoaopApplicationTests {

    @Autowired
    UserRepository userRepository;

    private IndexService indexService = new IndexServiceImpl();

    @Test
    void contextLoads() {
        indexService.testPrint();
    }

    @Test
    void proxyJDK(){
        IndexService proxy = (IndexService) Proxy.newProxyInstance(indexService.getClass().getClassLoader(),
                indexService.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 参数proxy:被代理的对象
                    // 参数method:执行的方法，代理对象执行哪个方法，method就是哪个方法
                    // 参数args:执行方法的参数
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        log.info("JDK 代理增强");
                        Object result = method.invoke(indexService, args);
                        return result;
                    }
                });
        proxy.testPrint();
    }

    @Test
    void proxyCGLib(){
        CGlibService cGlibService = new CGlibService();
        //创建CGlib核心对象
        Enhancer enhancer = new Enhancer();
        //设置父类
        //enhancer.setSuperclass(cGlibService.getClass());
        enhancer.setSuperclass(CGlibService.class);
        //设置回调
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                log.info("测试CGl代理");
                Object result = method.invoke(cGlibService, objects);
                return result;
            }
        });
        CGlibService proxy = (CGlibService)enhancer.create();
        proxy.testPrint();

    }

    @Test
    void testJpa(){
        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(data);

        userRepository.save(new SysUser(null,"aa","aa123456","aa@126.com","aa",formattedDate));
        userRepository.save(new SysUser(null,"bb","bb123456","bb@126.com","bb",formattedDate));
        userRepository.save(new SysUser(null,"cc","cc123456","cc@126.com","cc",formattedDate));

        userRepository.findAll().size();
        userRepository.findSysUserByUserNameAndEmail("bb","bb@126.com").getNickName();
        userRepository.delete(userRepository.findSysUserByUserName("aa"));
    }

}
