package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Long_time_record;
import com.ohd_project.ohd_project.entity.Path;
import com.ohd_project.ohd_project.entity.Record;
import com.ohd_project.ohd_project.service.LongTimeRecordService;
import com.ohd_project.ohd_project.service.impl.PathServiceImpl;
import com.ohd_project.ohd_project.service.impl.RecordServiceImpl;
import com.ohd_project.ohd_project.utils.ResultVO;
import com.ohd_project.ohd_project.utils.TransformText;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 记录管理控制器类
 */
@RestController
@RequestMapping("/records")
public class RecordController {

    @Resource
    private RecordServiceImpl recordService;

    @Resource
    private LongTimeRecordService longTimeRecordService;

    @Resource
    private PathServiceImpl pathService;

    // ANSI 转义码
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";


    /**
     * 根据事件类别获取记录列表
     *
     * @param category 事件类别
     * @return 返回事件类别对应的记录列表
     */
    @GetMapping("/category/{category}")
    public List<Record> getRecordsByCategory(@PathVariable int category) {
        List<Record> records = recordService.getRecordsByCategory(category);
        System.out.println("Records: " + records); // 添加打印语句
        return records;
    }

    @GetMapping("/setPath")
    public void setPath(@PathVariable String LogPath) {
        Path path = new Path(LogPath, "Log");
        pathService.Update(path);
    }

    /**
     * 获取异常记录并统计房间号和事件类别的数量
     * 出现报警则初始化报警器并报警，直到弹窗给出应答
     * 五秒执行一次
     */
    @Scheduled(fixedRate = 5 * 1000)
    public void getAbnormal() {
        List<Record> records = recordService.getRecordsByCategory(1);

        // 统计不同房间号和事件类别的数量
        Map<String, Map<String, Integer>> roomEventCountMap = new HashMap<>();

        for (Record r : records) {
            // 获取房间号和事件类别
            String roomNumber = r.getPlace();
            String eventCategory = r.getEvent();

            // 获取当前房间号的统计Map
            Map<String, Integer> eventCountMap = roomEventCountMap.getOrDefault(roomNumber, new HashMap<>());

            // 统计事件类别数量
            eventCountMap.put(eventCategory, eventCountMap.getOrDefault(eventCategory, 0) + 1);

            // 将当前房间号的统计Map放回总的统计Map
            roomEventCountMap.put(roomNumber, eventCountMap);
        }

        // 输出统计结果
        for (Map.Entry<String, Map<String, Integer>> roomEntry : roomEventCountMap.entrySet()) {
            String roomNumber = roomEntry.getKey();
            Map<String, Integer> eventCountMap = roomEntry.getValue();

            System.out.println("Room Number: " + roomNumber);

            for (Map.Entry<String, Integer> eventEntry : eventCountMap.entrySet()) {
                String eventCategory = eventEntry.getKey();
                int count = eventEntry.getValue();
                if (count >= 10) {
                    //打印红色报警信息
                    System.out.println("=====================================================");
                    System.out.println(ANSI_RED + "出现异常：" + eventCategory + "\n地点：" + roomNumber + "\n次数：" + count + ANSI_RESET);
                    System.out.println("=====================================================");
                    LocalDateTime currentTime = LocalDateTime.now();
                    Long_time_record long_time_record = new Long_time_record(recordService.generateId(),
                            currentTime, 1, eventCategory, roomNumber);
                    //保存长期记录
                    longTimeRecordService.MyInsert(long_time_record);
                    //初始化报警线程
                    Alarm alarm = new Alarm(eventCategory, roomNumber);
                    //报警线程启动
                    alarm.start();
                    return;
                }
                System.out.println("=============");
                System.out.println("当前无异常");
                System.out.println("=============");
            }
            System.out.println();
        }
    }

    public static class Alarm extends Thread {
        private final String eventCategory;
        private final String roomNumber;

        Alarm(String eventCategory, String roomNumber) {
            this.eventCategory = eventCategory;
            this.roomNumber = roomNumber;
        }

        public void run() {
            int a = 20;
            while (a-- > 0) {
                TransformText.textToSpeech(roomNumber + "号房间出现" + eventCategory);
            }
        }
    }

    /**
     * 获取所有记录列表
     *
     * @return 返回所有记录的列表
     */
    @GetMapping("/getRecords")
    private List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }
}
