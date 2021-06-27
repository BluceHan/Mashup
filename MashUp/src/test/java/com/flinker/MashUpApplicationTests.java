package com.flinker;

import com.flinker.web.service.MashUpService;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MashUpApplication.class)
public class MashUpApplicationTests {

    @Resource
    private MashUpService mashUpService;

    @Test
    public void test1() {
        String s = mashUpService.parseUrl("5b11f4ce-a62d-471e-81fc-a69a8278c7da");
        System.out.println(s);
    }

}
