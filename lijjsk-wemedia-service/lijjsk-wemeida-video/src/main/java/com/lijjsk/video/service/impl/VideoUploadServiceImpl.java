package com.lijjsk.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.common.constants.VideoConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.video.dtos.VideoBriefDto;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.utils.common.FFmpegUtils;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.service.VideoTaskService;
import com.lijjsk.video.service.VideoUploadService;
import com.minio.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class VideoUploadServiceImpl extends ServiceImpl<VideoMapper,Video> implements VideoUploadService {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 接收用户上传的视频文件
     * @param multipartFile 文件上传接口
     * @return 统一结果返回体
     */
    @Override
    @Transactional
    public ResponseResult uploadVideo(Integer userId,MultipartFile multipartFile) {
        //检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String packageName = userId.toString();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取原视频名称
        String originalVideoName = multipartFile.getOriginalFilename();
        //获取视频原格式
        String videoType = originalVideoName.substring(originalVideoName.lastIndexOf(".")+1);
        String videoUrl = null;
        try {
            videoUrl = fileStorageService.uploadVideoFile(packageName, uuid, originalVideoName, multipartFile.getInputStream(),videoType);
            log.info("上传视频到minio中,videoUrl:{}",videoUrl);
        } catch (IOException e) {
            log.error("上传视频文件失败");
            e.printStackTrace();
        }
        //TODO 默认保存视频第一帧的图片作为视频的封面
        MultipartFile image = FFmpegUtils.getThumbnailFromMultipartFile(multipartFile);
        //获取原图片名
        String originalImageName = image.getOriginalFilename();
        //获取原图片格式
        String imageUrl = null;
        try {
            imageUrl = fileStorageService.uploadImgFile(packageName,uuid,originalImageName,image.getInputStream());
            log.info("上传视频封面到minio中,videoUrl:{}",imageUrl);
        } catch (IOException e) {
            log.error("上传视频封面失败");
            e.printStackTrace();
        }
        //保存视频文件相关信息到数据库中
        Video video = new Video();
        //设置视频uuid
        video.setUuid(uuid);
        //设置视频url
        video.setVideoUrl(videoUrl);
        //设置视频封面url
        video.setCoverUrl(imageUrl);
        //设置视频状态初始为待审核
        video.setStatus(0);
        video.setUserId(userId);
        save(video);
        VideoBriefDto videoBriefDto = new VideoBriefDto(video);
        return ResponseResult.okResult(videoBriefDto);
    }


    @Override
    @Transactional
    public ResponseResult uploadVideoInfo(MultipartFile image,Integer id,String title,String briefIntro) {
        Video video = getById(id);
        video.setTitle(title);
        video.setBriefIntro(briefIntro);
        //检查封面是否存在
        if(image == null || image.getSize() == 0){
            //封面不存在的情况下由于默认已经保存了默认封面，所以不做过多处理，整个业务流程结束
            save(video);
            return ResponseResult.okResult(null);
        }else {
            //封面存在的情况下，删除原有封面，保存新封面
            //压缩封面
            //ImageUtils.compressImage()
            //视频所在文件夹为用户Id  这里先设置一个默认值用于测试
            Integer userId = 1;
            String packageName = userId.toString();
            //上传图片到minio中
            //获取封面图片名
            String originalFilename = image.getOriginalFilename();
            String imageUrl = null;
            try {
                imageUrl = fileStorageService.uploadImgFile(packageName, video.getUuid(), originalFilename,image.getInputStream());
                log.info("上传视频封面到minio中,videoUrl:{}",imageUrl);
            } catch (IOException e) {
                log.error("上传视频封面失败");
                e.printStackTrace();
            }
            //获取返回的url保存到数据库中
            video.setCoverUrl(imageUrl);
            video.setStatus(VideoConstants.WAIT_FOR_REVIEW);
            updateById(video);
            rabbitTemplate.convertAndSend("video-review-queue", video.getId());
            VideoBriefDto videoBriefDto = new VideoBriefDto(video);
            return ResponseResult.okResult(videoBriefDto);
        }
    }
}
