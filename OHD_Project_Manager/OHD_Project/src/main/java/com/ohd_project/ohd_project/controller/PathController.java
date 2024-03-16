package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Path;
import com.ohd_project.ohd_project.service.impl.PathServiceImpl;
import com.ohd_project.ohd_project.utils.ResultVO;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Paths")
public class PathController {
    @Resource
    private PathServiceImpl pathService;
    /**
     * 添加Log路径
     * @return 添加信息
     */
    @RequestMapping(value = "/LogPath", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO editInfo(@RequestBody Map<String, String> formData) {
        String Path = formData.get("Path");
        Path path = new Path(Path, "Log");
        pathService.Update(path);
        return new ResultVO(1001, "添加成功", null);
    }

}
