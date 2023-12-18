package com.lijjsk.social.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.barrage.dtos.BarrageDto;
import com.lijjsk.social.service.BarrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wemedia/barrage")
public class BarrageController {
    @Autowired
    private BarrageService barrageService;

    @PostMapping("/save/barrage")
    public ResponseResult saveBarrage(@RequestBody BarrageDto barrageDto) throws JsonProcessingException {
        return barrageService.saveBarrage(barrageDto);
    }
    @DeleteMapping("/delete/barrage")
    public ResponseResult deleteBarrage(@RequestParam Integer barrageId) throws JsonProcessingException {
        return barrageService.deleteBarrage(barrageId);
    }
    @GetMapping("/get/barrage")
    public ResponseResult getBarrage(@RequestParam Integer videoId,
                                     @RequestParam Integer startTime,
                                     @RequestParam Integer endTime){
        return barrageService.getBarrage(videoId,startTime,endTime);

    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
