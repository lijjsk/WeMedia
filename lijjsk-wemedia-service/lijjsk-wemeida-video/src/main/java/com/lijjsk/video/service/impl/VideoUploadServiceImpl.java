package com.lijjsk.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.utils.common.FFmpegUtils;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.service.VideoTaskService;
import com.lijjsk.video.service.VideoUploadService;
import com.minio.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private VideoTaskService videoTaskService;
    /**
     * 接收用户上传的视频文件
     * @param multipartFile 文件上传接口
     * @return 统一结果返回体
     */
    @Override
    public ResponseResult uploadVideo(MultipartFile multipartFile) {
        //检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 获取当前认证的用户信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // 检查用户是否已认证
//        if (authentication != null && authentication.isAuthenticated()) {
//            // 获取用户详细信息
//            Object principal = authentication.getPrincipal();
//            //这里UserDetails需要实现getUserId()方法
//            //String userId = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
//
//            // 在这里你可以使用用户信息进行进一步的处理，比如记录日志，关联用户和上传的视频等
//            return ResponseResult.okResult(video);
//        } else {
//            // 处理未认证的情况，可能返回错误信息或者进行其他逻辑处理
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }

        //生成视频唯一主键
//        String filename = UUID.randomUUID().toString().replace("-", "");
        //视频所在文件夹为用户Id  这里先设置一个默认值用于测试
        Integer userId = 1;
        String packageName = userId.toString();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取原视频名称
        String originalVideoName = multipartFile.getOriginalFilename();
        String videoUrl = null;
        try {
            videoUrl = fileStorageService.uploadVideoFile(packageName, uuid, originalVideoName, multipartFile.getInputStream());
            log.info("上传视频到minio中,videoUrl:{}",videoUrl);
        } catch (IOException e) {
            log.error("上传视频文件失败");
            e.printStackTrace();
        }
        //TODO 默认保存视频第一帧的图片作为视频的封面
        MultipartFile image = FFmpegUtils.getThumbnailFromMultipartFile(multipartFile);
        String originalImageName = image.getOriginalFilename();
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
        //从principal中获取当前登录用户的信息
        video.setUserId(userId);
        save(video);
        return ResponseResult.okResult(video);
    }


    @Override
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
            updateById(video);
            videoTaskService.addVideoToTask(video.getId(),new Date());
            return ResponseResult.okResult(video);
        }
    }
}
