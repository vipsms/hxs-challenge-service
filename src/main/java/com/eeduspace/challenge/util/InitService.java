package com.eeduspace.challenge.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eeduspace.challenge.persist.dao.WeeklyRankingMapper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:
 */
@Component
public class InitService {
    private static final Logger logger = LoggerFactory.getLogger(InitService.class);

    @Inject
    private DataSource dataSource;
    @PostConstruct
    @Transactional
    public void init() {
        try {
            logger.info("begin init portal!");
            //TODO 初始化 签到规则
            //TODO 验证是否有签到规则
            Connection conn = getConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            runner.runScript(Resources.getResourceAsReader("conf/sign_rule.sql"));
            logger.info("portal init successful!");
        } catch (Exception e) {
            logger.error("portal init error!", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
