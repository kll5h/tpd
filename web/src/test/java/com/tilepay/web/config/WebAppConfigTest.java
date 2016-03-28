package com.tilepay.web.config;

import com.tilepay.web.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ViewResolver;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class WebAppConfigTest {

    @Autowired
    private ViewResolver viewResolver;

    @Test
    public void setupViewResolver() throws Exception {
        //assertTrue(viewResolver instanceof ThymeleafViewResolver);
    }
}