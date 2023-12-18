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
    public ResponseResult stopAdvertisement(Integer advertisementId);

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
    public ResponseResult getUserAdvertisement(Integer advertiserId);
    /**
     * 展示广告
     */
    public ResponseResult showAdvertisement(Integer advertisementId);
    /**
     * 点击广告
     * @param advertisementId
     * @return
     */
    public ResponseResult clickAdvertisement(Integer advertisementId);

    /**
     * 转化广告
     * @param advertisementId
     * @return
     */
    public ResponseResult converseAdvertisement(Integer advertisementId);

    /**
     * 获取广告效果信息
     * @param advertisementId
     * @return
     */
    public ResponseResult getAdvertisementInfo(Integer advertisementId);
}
