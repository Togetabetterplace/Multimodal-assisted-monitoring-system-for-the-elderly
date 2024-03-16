package com.ohd_project.ohd_project;

import com.ohd_project.ohd_project.entity.Record;
import com.ohd_project.ohd_project.service.RecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) // 使用Spring支持的JUnit5扩展
@SpringBootTest
public class RecordServiceTest {

    @Autowired
    private RecordService recordService;

    @Test
    public void testGetRecordsByCategory() {
        int categoryToTest = 1;
        List<Record> records = recordService.getRecordsByCategory(categoryToTest);

        assertNotNull(records);
        // 进一步添加其他断言，确保查询结果符合预期
    }
}
