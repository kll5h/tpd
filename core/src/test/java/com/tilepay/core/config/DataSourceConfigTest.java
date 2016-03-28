package com.tilepay.core.config;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { DataSourceConfig.class, CoreAppConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public class DataSourceConfigTest {

    @Inject
    private DataSource dataSource;

    @Test
    public void dataSource() throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select 1 dual");
        preparedStatement.execute();
    }
}