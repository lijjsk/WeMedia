package com.lijjsk.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.VideoConstants;
import com.lijjsk.model.common.dtos.OriginalFormatResult;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.model.wemedia.video.pojos.VideoResolution;
import com.lijjsk.utils.common.FFmpegUtils;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.mapper.VideoResolutionMapper;
import com.lijjsk.video.service.VideoCompressService;
import com.lijjsk.video.service.VideoPublishService;
import com.lijjsk.video.service.VideoTaskService;
import com.minio.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class VideoCompressServiceImpl extends ServiceImpl<VideoResolutionMapper,VideoResolution> implements VideoCompressService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private FileStorageService fileStorageService;
    /**
     * 视频压缩
     * @param id
     */
    @Override
    public ResponseResult compressVideo(Integer id) {
        Video video = videoMapper.selectById(id);
        String videoUrl = video.getVideoUrl();
        String videoTitle = video.getTitle();
        // 从fileStorageService.downLoadFile(videoUrl)获取到的视频字节数组
        OriginalFormatResult originalFormatResult = fileStorageService.downLoadFile(videoUrl);
        byte[] videoBytes = originalFormatResult.getFileContent();
        String contentType = originalFormatResult.getOriginalFormat();
        // 通过字符串分割获取文件扩展名
        String[] contentTypeParts = contentType.split("/");
        String fileExtension = contentTypeParts[1];
        // 创建一个ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(videoBytes) {
            @Override
            public String getFilename() {
                if (contentTypeParts.length == 2) {
                    // 设置文件名，可以根据需要修改
                    return videoTitle + "." + fileExtension;
                } else {
                    // 处理未知的contentType格式
                    return videoTitle;
                }
            }
        };
        // 创建一个MockMultipartFile对象
        MultipartFile multipartFile = new MockMultipartFile(videoTitle, resource.getFilename(), "video/"+fileExtension, resource.getByteArray());
        //获取视频时长

        //视频压缩为1080p 60hz
        MultipartFile videoTo1080p60hz = FFmpegUtils.compressVideoTo1080p60hz(multipartFile, videoTitle);

//        //视频压缩为1080p 30hz
//        MultipartFile videoTo1080p30hz = FFmpegUtils.compressVideoTo1080p30hz(multipartFile,videoTitle);
//        //视频压缩为720p 30hz
//        MultipartFile videoTo720p30hz = FFmpegUtils.compressVideoTo720p30hz(multipartFile,videoTitle);
//        //视频压缩为480p 30hz
//        MultipartFile videoTo480p30hz = FFmpegUtils.compressVideoTo480p30hz(multipartFile,videoTitle);
//        //视频压缩为320p 30hz
//        MultipartFile videoTo320p30hz = FFmpegUtils.compressVideoTo320p30hz(multipartFile,videoTitle);
        upload(videoTo1080p60hz,video);
//        upload(videoTo1080p30hz,video);
//        upload(videoTo720p30hz,video);
//        upload(videoTo480p30hz,video);
//        upload(videoTo320p30hz,video);
        video.setStatus(VideoConstants.WAIT_FOR_PUBLISH);
        videoMapper.updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    //上传压缩好的视频
    public void upload(MultipartFile multipartFile,Video video){
        Integer userId = video.getUserId();
        String packageName = userId.toString();
        //获取原视频名称
        String originalVideoName = multipartFile.getOriginalFilename();
        //获取视频原格式
        String videoType = originalVideoName.substring(originalVideoName.lastIndexOf(".")+1);
        String videoUrl = null;
        try {
            videoUrl = fileStorageService.uploadVideoFile(packageName, video.getUuid(), originalVideoName, multipartFile.getInputStream(),videoType);
            log.info("上传视频到minio中,videoUrl:{}",videoUrl);
        } catch (IOException e) {
            log.error("上传视频文件失败");
            e.printStackTrace();
        }
        VideoResolution videoResolution = new VideoResolution();
        videoResolution.setVideoId(video.getId());
        String type = originalVideoName.substring(0, originalVideoName.indexOf("_"));
        videoResolution.setType(type);
        videoResolution.setUrl(videoUrl);
        save(videoResolution);
    }


}
