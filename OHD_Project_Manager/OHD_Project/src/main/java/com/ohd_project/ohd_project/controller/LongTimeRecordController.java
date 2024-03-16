package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Long_time_record;
import com.ohd_project.ohd_project.entity.Path;
import com.ohd_project.ohd_project.service.LongTimeRecordService;
import com.ohd_project.ohd_project.service.impl.PathServiceImpl;
import com.ohd_project.ohd_project.utils.LogGenerator;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 长时间记录控制器，处理与长时间记录相关的HTTP请求
 */
@RestController
@RequestMapping("/Lrecords")
public class LongTimeRecordController {

    @Resource
    private LongTimeRecordService longTimeRecordService;

    @Resource
    private PathServiceImpl pathService;


    private static LocalDateTime lastLogGenerationTime = LocalDateTime.MIN; // 上一次生成日志的时间
    /**
     * 根据指定类别获取长时间记录列表
     *
     * @param  longTimeRecords 类别参数
     * @return 返回符合类别的长时间记录列表
     */
    public void generateLogs(List<Long_time_record> longTimeRecords) {
        LocalDateTime currentTime = LocalDateTime.now();             //获取当前时间
        LogGenerator logGenerator = new LogGenerator();              //初始化生成器
        Path path = pathService.getByDisc("Log");
        String path1 = path.getPath();                               //获取路径
        logGenerator.setLogFilePath(path1);                          //设定路径
        // 检查是否已经过去了10天
        if (Duration.between(lastLogGenerationTime, currentTime).toDays() >= 10) {
            logGenerator.generateLog(longTimeRecords);
            // 更新上一次生成日志的时间
            lastLogGenerationTime = currentTime;
        }
    }
    /**
     * 根据指定类别获取长时间记录列表
     *
     * @param category 类别参数
     * @return 返回符合类别的长时间记录列表
     */
    @GetMapping("/category/{category}")
    public List<Long_time_record> getLRecordsByCategory(@PathVariable int category) {
        // 调用服务层获取符合类别的长时间记录列表
        List<Long_time_record> Lrecords = longTimeRecordService.getLRecordsByCategory(category);
        generateLogs(Lrecords);
        System.out.println("LRecords: " + Lrecords); // 添加打印语句

        return Lrecords;
    }

    /**
     * 获取所有长时间记录列表
     *
     * @return 返回所有长时间记录列表
     */
    @GetMapping("/getLRecords")
    private List<Long_time_record> getAllRecords() {
        return longTimeRecordService.getAllLRecords();
    }
}
