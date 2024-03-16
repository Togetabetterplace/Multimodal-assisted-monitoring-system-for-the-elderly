package com.ohd_project.ohd_project;

import com.ohd_project.ohd_project.entity.Online_Caregiver;
import com.ohd_project.ohd_project.service.OnlineCaregiverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // 使用Spring支持的JUnit5扩展
@SpringBootTest
public class OnlineCaregiverServiceTest {
    @Autowired
    private OnlineCaregiverService onlineCaregiverService;

    @Test
    public void UserTest() {
//        onlineCaregiverService.insertOnlineCaregiver("101","123");

        Online_Caregiver a = onlineCaregiverService.findOnlineCaregiverById("101");

        System.out.println(a.getCaregiverId());
        System.out.println(a.getResponsibleGroupId());

//        String a = userService.checkUser(ch);
        onlineCaregiverService.deleteOnlineCaregiver(a.getCaregiverId());
        //注意：要保证表中的字段名和实际查询的变量名一致。

        // 进一步添加其他断言，确保查询结果符合预期
    }
}
