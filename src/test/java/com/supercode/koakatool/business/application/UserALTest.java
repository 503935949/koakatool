package com.supercode.koakatool.business.application;

import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.query.UserQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Component
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional()
public class UserALTest {

    @Autowired
    private UserAL al;

    @Test
   // @Commit
    public void insert() {
        int a = al.insert(new UserDomain("qwe2","qwe222"));
        System.out.println(a);
        assertNull(a);
    }

    @Test
    public void findByWhereForList() {
        List a  =al.findByWhereForList(new UserQuery());
        System.out.println(a);
        assertNull(a);
        assertTrue(a.size()>0);
    }
}