package com.hs.demo.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试CGLib代理
 */
@Slf4j
public class CGlibService {

    public void testPrint(){
        log.info("测试CGlib代理，无实现接口");
    }
}
