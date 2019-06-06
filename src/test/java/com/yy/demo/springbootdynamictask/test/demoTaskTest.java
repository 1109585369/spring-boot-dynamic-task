package com.yy.demo.springbootdynamictask.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class demoTaskTest {

    @Autowired
    demoTask demoTask;

    @Test
    public void taskWithParams() {
    }

    @Test
    public void taskNoParams() {
    }
}