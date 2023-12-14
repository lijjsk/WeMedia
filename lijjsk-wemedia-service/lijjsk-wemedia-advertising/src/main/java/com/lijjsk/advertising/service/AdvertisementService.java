package com.lijjsk.advertising.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.advertising.pojos.Advertisement;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementService extends IService<Advertisement> {
    /**
     * 增加广告
     * @param advertiserId
     * @param content
     * @param duration
     * @param position
     * @param multipartFile
     * @return
     */
    public ResponseResult addAdvertisement(Integer advertiserId,
                                           String content,
                                           Integer duration,
                                           Integer position,
                                           MultipartFile multipartFile);

    /**
     * 停用广告
     * @param advertisementId
     * @return
     */
    public ResponseResult stopAdvertisement(String advertisementId);

    /**
     * 获取所有广告
     * @return
     */
    public ResponseResult getAllAdvertisement();

    /**
     * 获取用户的广告
     * @param advertiserId
     * @return
     */
    public ResponseResult getUserAdvertisement(String advertiserId);

    /**
     * 点击广告
     * @param advertisementId
     * @return
     */
    public ResponseResult clickAdvertisement(String advertisementId);

    /**
     * 转化广告
     * @param advertisementId
     * @return
     */
    public ResponseResult converseAdvertisement(String advertisementId);

    /**
     * 获取广告点击率
     * @param advertisementId
     * @return
     */
    public ResponseResult getAdvertisementCTR(String advertisementId);

    /**
     * 获取广告转化率
     * @param advertisementId
     * @return
     */
    public ResponseResult getAdvertisementConversionRate(String advertisementId);
}
