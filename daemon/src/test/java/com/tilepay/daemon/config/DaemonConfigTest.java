package com.tilepay.daemon.config;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = DaemonConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaemonConfigTest {

    @Ignore
    @Test
    public void task() throws Exception {

        Thread thread = new Thread(() -> {
            while (true) {
            }
        });
        thread.start();
        thread.join();

    }
}