package com.lijjsk.advertising.controller;

import com.lijjsk.advertising.service.PositionService;
import com.lijjsk.model.advertising.dtos.PositionDto;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wemedia/advertising/position")
public class PositionController {
    @Autowired
    private PositionService positionService;
    @GetMapping("/get/positionList")
    public ResponseResult getPositionList(){
        return positionService.getPositionList();
    }
    @PostMapping("/add/position")
    public ResponseResult addPosition(@RequestBody PositionDto positionDto){
        return positionService.addPosition(positionDto);
    }
    @DeleteMapping("/delete/position")
    public ResponseResult deletePosition(@RequestParam Integer positionId){
        return positionService.deletePosition(positionId);
    }
    @PutMapping("/add/positionNum")
    public ResponseResult addPosition(@RequestParam Integer positionId){
        return positionService.addPositionNum(positionId);
    }
    @PutMapping("/reduce/positionNum")
    public ResponseResult reducePosition(@RequestParam Integer positionId){
        return positionService.reducePositionNum(positionId);
    }
    @PutMapping("/update/position")
    public ResponseResult updatePosition(@RequestBody AdvertisementPosition advertisementPosition){
        return positionService.updatePosition(advertisementPosition);
    }
}
