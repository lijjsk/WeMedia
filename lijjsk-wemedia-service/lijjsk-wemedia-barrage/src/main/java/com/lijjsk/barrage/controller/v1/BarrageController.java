package com.lijjsk.barrage.controller.v1;

import com.lijjsk.barrage.service.BarrageService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.barrage.dtos.BarrageDto;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wemedia/barrage")
@CrossOrigin("*")
public class BarrageController {
    @Autowired
    private BarrageService barrageService;

    @PostMapping("/save/barrage")
    public ResponseResult saveBarrage(@RequestBody BarrageDto barrageDto){
        return barrageService.saveBarrage(barrageDto);
    }
    @DeleteMapping("/delete/barrage")
    public ResponseResult deleteBarrage(@RequestParam Integer barrageId){
        return barrageService.deleteBarrage(barrageId);
    }
    @GetMapping("/get/barrage")
    public ResponseResult getBarrage(@RequestParam Integer videoId,
                                     @RequestParam Integer startTime,
                                     @RequestParam Integer endTime){
        return barrageService.getBarrage(videoId,startTime,endTime);
    }
}
