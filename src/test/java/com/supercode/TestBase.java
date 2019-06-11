package com.supercode;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Component
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional()
public class TestBase {
}
