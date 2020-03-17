package com.hs.demo.util.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定义日志业务
 */
@Slf4j
@Service
public class SysLogService {
    public boolean save(SysLogBO sysLogBo){
        log.info(sysLogBo.getParams());
        return true;
    }
}
