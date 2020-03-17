package com.hs.demo.service.impl;

import com.hs.demo.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Override
    public void testPrint() {
        log.info("面向切面编程{}",System.currentTimeMillis());
    }
}
