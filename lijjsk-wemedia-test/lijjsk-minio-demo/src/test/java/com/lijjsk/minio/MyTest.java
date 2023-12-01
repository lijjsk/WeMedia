package com.lijjsk.minio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MinIOApplication.class)
public class MyTest {
    @Test
    public void test(){
        System.out.println("test");
    }
}
