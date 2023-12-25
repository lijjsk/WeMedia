package com.lijjsk.advertising.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Timestamp;
import java.text.DecimalFormat;
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
    @Autowired
    private AdvertisementMapper advertisementMapper;
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
    @Transactional
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
        //初始化广告效果数据
        advertisement.setShows(0);
        advertisement.setConversions(0);
        advertisement.setClicks(0);
        advertisement.setCtr(0.0);
        advertisement.setConversionRate(0.0);
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
    @Transactional
    public ResponseResult stopAdvertisement(Integer advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setStatus(AdvertisementConstants.END_PUTTING_IN);
        updateById(advertisement);
        positionService.addPositionNum(advertisement.getPosition());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)  // 每天凌晨触发一次
    public void stopAdvertisementAuto(){
        log.info("定时检查，停止投放广告");
        List<Advertisement> activeAdvertisements = advertisementMapper.selectList(Wrappers.<Advertisement>lambdaQuery()
                .eq(Advertisement::getStatus,AdvertisementConstants.PUTTING_IN));
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
        List<Advertisement> advertisementList = advertisementMapper.selectList(Wrappers.<Advertisement>lambdaQuery()
                .eq(Advertisement::getStatus,AdvertisementConstants.PUTTING_IN));
        return ResponseResult.okResult(advertisementList);
    }

    /**
     * 获取用户的广告
     * @param advertiserId
     * @return
     */
    @Override
    public ResponseResult getUserAdvertisement(Integer advertiserId) {
        List<Advertisement> advertisementList = advertisementMapper.selectList(Wrappers.<Advertisement>lambdaQuery()
                .eq(Advertisement::getAdvertiserId,advertiserId));
        return ResponseResult.okResult(advertisementList);
    }

    /**
     * 展示广告
     *
     * @param advertisementId
     */
    @Override
    public ResponseResult showAdvertisement(Integer advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setShows(advertisement.getShows()+1);
        updateById(advertisement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 点击广告
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult clickAdvertisement(Integer advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setClicks(advertisement.getClicks()+1);
        updateById(advertisement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 转化广告
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult converseAdvertisement(Integer advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        advertisement.setConversions(advertisement.getConversions()+1);
        updateById(advertisement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 获取广告效果信息
     * @param advertisementId
     * @return
     */
    @Override
    public ResponseResult getAdvertisementInfo(Integer advertisementId) {
        Advertisement advertisement = getById(advertisementId);
        DecimalFormat df = new DecimalFormat("#.###");

        if(advertisement.getShows() != 0) {
            advertisement.setCtr(Double.parseDouble(df.format((double)advertisement.getClicks() / (double)advertisement.getShows())));
        } else {
            advertisement.setCtr(0.0);
        }

        if (advertisement.getClicks() != 0) {
            advertisement.setConversionRate(Double.parseDouble(df.format((double)advertisement.getConversions() / (double)advertisement.getClicks())));
        } else {
            advertisement.setConversionRate(0.0);
        }

        updateById(advertisement);
        return ResponseResult.okResult(advertisement);
    }
    @Scheduled(fixedRate = 1000 * 10)
    @Transactional
    public void updateAdvertisementInfo() {
        log.info("定时刷新广告信息");
        List<Advertisement> advertisementList = advertisementMapper.selectList(Wrappers.<Advertisement>lambdaQuery()
                .eq(Advertisement::getStatus,AdvertisementConstants.PUTTING_IN));
        DecimalFormat df = new DecimalFormat("#.###");
        for (Advertisement advertisement : advertisementList) {
            if(advertisement.getShows() != 0) {
                advertisement.setCtr(Double.parseDouble(df.format((double)advertisement.getClicks() / (double)advertisement.getShows())));
            } else {
                advertisement.setCtr(0.0);
            }

            if (advertisement.getClicks() != 0) {
                advertisement.setConversionRate(Double.parseDouble(df.format((double)advertisement.getConversions() / (double)advertisement.getClicks())));
            } else {
                advertisement.setConversionRate(0.0);
            }

            updateById(advertisement);
        }
    }
}
