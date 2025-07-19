package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.video.Area;
import cn.edu.scnu.danmakutv.video.service.AreaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Tag(name = "视频分区操作相关接口")
@RestController
@RequestMapping("/video")
public class AreaController {
    @Resource
    private AreaService areaService;

    @Operation(summary = "获取所有视频分区")
    @GetMapping("/all-area")
    public CommonResponse<List<Area>> getAllAreas() {
        List<Area> areas = areaService.list();
        return CommonResponse.success(areas);
    }
}
