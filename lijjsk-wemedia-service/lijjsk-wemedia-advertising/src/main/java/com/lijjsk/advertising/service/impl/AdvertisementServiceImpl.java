package com.lijjsk.advertising.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.advertising.mapper.AdvertisementMapper;
import com.lijjsk.advertising.service.AdvertisementService;
import com.lijjsk.advertising.service.PositionService;
import com.lijjsk.common.constants.AdvertisementConstants;
import com.lijjsk.model.advertising.dtos.AdvertisementDataDto;
import com.lijjsk.model.advertising.pojos.Advertisement;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.utils.common.FFmpegUtils;
import com.minio.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementMapper, Advertisement> implements AdvertisementService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private PositionService positionService;
    /**
     * 增加广告
     * @param advertiserId
     * @param content
     * @param duration
     * @param position
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult addAdvertisement(Integer advertiserId, String content, Integer duration, Integer position, MultipartFile multipartFile) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiserId(advertiserId);
        advertisement.setContent(content);
        advertisement.setDuration(duration);
        advertisement.setStatus(AdvertisementConstants.PUTTING_IN);
        //设置开始投放时间
        Date now = new Date();
        advertisement.setStartTime(now);
        //设置结束投放时间
        long durationInMillis = (long) duration * 24 * 60 * 60 * 1000;
        long endTimeMillis = now.getTime() + durationInMillis;
        Date endTime = new Date(endTimeMillis);
        advertisement.setEndTime(endTime);
        advertisement.setPosition(position);
        MultipartFile image = FFmpegUtils.getThumbnailFromMultipartFile(multipartFile);
        //获取原图片名
        String originalImageName = image.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String imageUrl = null;
        try {
            imageUrl = fileStorageService.uploadImgFile(String.valueOf(advertiserId),uuid,originalImageName,image.getInputStream());
            log.info("广告图片到minio中,imageUrl:{}",imageUrl);
        } catch (IOException e) {
            log.error("上传视频封面失败");
            e.printStackTrace();
        }
        advertisement.setPictureUrl(imageUrl);
        save(advertisement);
        positionService.reducePositionNum(position);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 停用广告
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult stopAdvertisement(String advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setStatus(AdvertisementConstants.END_PUTTING_IN);
        updateById(advertisement);
        positionService.addPositionNum(advertisement.getPosition());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Scheduled(cron = "0 0 0 * * ?")  // 每天凌晨触发一次
    public void stopAdvertisementAuto(){
        QueryWrapper<Advertisement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", AdvertisementConstants.PUTTING_IN);
        List<Advertisement> activeAdvertisements = this.list(queryWrapper);

        // 获取当前时间
        long currentTimeMillis = System.currentTimeMillis();

        // 遍历正在投放的广告，检查是否结束，如果结束就停用
        for (Advertisement advertisement : activeAdvertisements) {
            if (advertisement.getEndTime().getTime() <= currentTimeMillis) {
                advertisement.setStatus(AdvertisementConstants.END_PUTTING_IN);
                updateById(advertisement);
            }
        }
    }

    /**
     * 获取所有广告
     * @return
     */
    @Override
    public ResponseResult getAllAdvertisement() {
        QueryWrapper<Advertisement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", AdvertisementConstants.PUTTING_IN);
        return ResponseResult.okResult(this.list(queryWrapper));
    }

    /**
     * 获取用户的广告
     * @param advertiserId
     * @return
     */
    @Override
    public ResponseResult getUserAdvertisement(String advertiserId) {
        QueryWrapper<Advertisement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("advertiserId",advertiserId);
        return ResponseResult.okResult(this.list(queryWrapper));
    }

    /**
     * 点击广告
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult clickAdvertisement(String advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setClicks(advertisement.getClicks()+1);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 转化广告
     *
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult converseAdvertisement(String advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setConversions(advertisement.getConversions()+1);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 获取广告点击率
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult getAdvertisementCTR(String advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        AdvertisementDataDto dto = new AdvertisementDataDto();
        BeanUtils.copyProperties(advertisement,dto);
        return ResponseResult.okResult(dto);
    }

    /**
     * 获取广告转化率
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult getAdvertisementConversionRate(String advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        AdvertisementDataDto dto = new AdvertisementDataDto();
        BeanUtils.copyProperties(advertisement,dto);
        return ResponseResult.okResult(dto);
    }
}
