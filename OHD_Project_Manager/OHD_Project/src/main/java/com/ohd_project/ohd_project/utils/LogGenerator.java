package com.ohd_project.ohd_project.utils;

import com.ohd_project.ohd_project.entity.Long_time_record;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 日志生成器（LogGenerator）用于生成监护日志，并将日志保存到指定路径。
 */
public class LogGenerator {

    private static String LOG_FILE_PATH; // 指定监护日志保存路径

    /**
     * 生成监护日志并保存到文件。
     *
     * @param longTimeRecords 包含监护记录的列表
     */
    public void generateLog(List<Long_time_record> longTimeRecords) {
        LocalDateTime currentTime = LocalDateTime.now(); // 获取当前时间
        String time = String.valueOf(currentTime);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH + "Log" + time + ".txt"))) {
            // 写入表头
            writer.write("ID\tTime\tCategory\tEvent\tPlace\n");

            // 写入日志数据
            for (Long_time_record record : longTimeRecords) {
                writer.write(formatLog(record) + "\n");
            }

            System.out.println("监护日志已生成，保存路径：" + LOG_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("保存监护日志时出错：" + e.getMessage());
        }
    }

    /**
     * 设置监护日志文件保存路径。
     *
     * @param logFilePath 日志文件保存路径
     */
    public void setLogFilePath(String logFilePath) {
        LOG_FILE_PATH = logFilePath;
    }

    /**
     * 格式化监护记录为字符串。
     *
     * @param record 监护记录对象
     * @return 格式化后的字符串
     */
    private String formatLog(Long_time_record record) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = record.getTime().format(formatter);

        return String.format("ID: %s, Time: %s, Category: %d, Event: %s, Place: %s",
                record.getID(), formattedTime, record.getCategory(), record.getEvent(), record.getPlace());
    }

}
