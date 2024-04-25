package com.yong.projectfp_2;

import com.yong.projectfp_2.service.FestivalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectFp2ApplicationTests {

    @Autowired
    FestivalService festivalService;
    @Test
    void contextLoads() {
        festivalService.getFestivalAddress(38);
    }

}

