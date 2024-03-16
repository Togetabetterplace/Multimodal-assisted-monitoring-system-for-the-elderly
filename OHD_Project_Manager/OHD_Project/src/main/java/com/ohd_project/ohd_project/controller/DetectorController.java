package com.ohd_project.ohd_project.controller;

// 导入相关类

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ohd_project.ohd_project.entity.Detector;
import com.ohd_project.ohd_project.mapper.DetectorMapper;
import com.ohd_project.ohd_project.service.DetectorService;
import com.ohd_project.ohd_project.utils.ResultVO;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * 检测器管理控制器类
 */
@Controller
@RequestMapping("/detectors")
public class DetectorController {

    // 注入服务层和 MyBatis Mapper
    @Resource
    private DetectorService detectorService;
    @Resource
    private DetectorMapper detectorMapper;

    /**
     * 控制器类的构造方法
     *
     * @param detectorMapper MyBatis Mapper
     */
    public DetectorController(DetectorMapper detectorMapper) {
        this.detectorMapper = detectorMapper;
    }

    /**
     * 映射到添加检测器页面
     */
    @RequestMapping("/add")
    public String add() {
        return "AddDetector";
    }

    /**
     * 映射到删除检测器页面
     */
    @RequestMapping("/del")
    public String del() {
        return "DelDetector";
    }

    /**
     * 处理添加检测器请求
     *
     * @param request 包含检测器信息的请求体
     * @return 返回添加结果的ResultVO对象
     */
    @RequestMapping(value = "/addDetector", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO addDetector(@RequestBody DetectorRequest request) {
        // 生成唯一的 Thread_ID
        String ThreadID = generateThreadId();
        // 创建 Detector 对象
        Detector detector = new Detector(request.getRoomNumber(), request.getCategory(), request.getSource(), ThreadID);
        // 调用服务层方法插入数据
        String threadID = detector.getThreadID();
        String roomNumber = detector.getRoomNumber();
        int category = detector.getCategory();
        String source = detector.getSource();
        int state = detector.getState();
        detectorService.insertNewDetector(threadID, roomNumber, category, source, state);
        return new ResultVO(1001, "检测器添加成功", null);
    }

    /**
     * 处理删除检测器请求
     *
     * @param request 包含要删除的检测器信息的请求体
     * @return 返回添加结果的ResultVO对象
     */
    @RequestMapping(value = "/DelDetector", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO DelDetector(@RequestBody DetectorRequest request) {
        String roomNumber = request.getRoomNumber();
        int Category = request.getCategory();
        String Source = request.getSource();
        detectorService.delDetector(roomNumber, Category, Source);
        return new ResultVO(1001, "检测器删除成功", null);
    }

    /**
     * 用于接收前端请求的 DTO（Data Transfer Object）类
     */
    @Getter
    static class DetectorRequest {
        private String roomNumber;
        private int category;
        private String source;

        // 添加getters和setters

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    /**
     * 生成唯一的 Thread_ID
     *
     * @return 返回生成的 Thread_ID
     */
    public String generateThreadId() {
        String newThreadId;
        do {
            newThreadId = generateRandomThreadId();
        } while (threadIdAlreadyExists(newThreadId));
        return newThreadId;
    }

    /**
     * 生成随机的 3 位数字字符串
     *
     * @return 返回生成的 thread_ID
     */
    private String generateRandomThreadId() {
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        return String.valueOf(randomNumber);
    }

    /**
     * 检查 thread_ID 是否已存在
     *
     * @param newThreadId 待检查的 thread_ID
     * @return 如果存在相同的 thread_ID，则返回 true；否则返回 false
     */
    private boolean threadIdAlreadyExists(String newThreadId) {
        // 使用 QueryWrapper 创建查询条件
        QueryWrapper<Detector> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threadID", newThreadId);

        // 调用 MyBatis Plus 提供的 count 方法统计符合条件的记录数
        int count = Math.toIntExact(detectorMapper.selectCount(queryWrapper));

        // 如果存在相同的 thread_ID，则返回 true；否则返回 false
        return count > 0;
    }
}
