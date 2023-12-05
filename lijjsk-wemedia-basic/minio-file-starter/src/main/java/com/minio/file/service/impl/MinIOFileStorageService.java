package com.minio.file.service.impl;


import com.lijjsk.model.common.dtos.OriginalFormatResult;
import com.minio.file.config.MinIOConfig;
import com.minio.file.config.MinIOConfigProperties;
import com.minio.file.service.FileStorageService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
@Service
public class MinIOFileStorageService implements FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    private final static String separator = "/";

    /**
     * @param dirPath
     * @param filename  userId/uuid/filename/fileType
     * @return
     */
    public String builderFilePathType(String dirPath,String uuid,String filename,String fileType) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if(!StringUtils.isEmpty(dirPath)){
            stringBuilder.append(dirPath).append(separator);
        }
        stringBuilder.append(uuid).append(separator);
        // 获取文件的前缀名
        int lastIndex = filename.lastIndexOf(".");
        String prefix = filename.substring(0, lastIndex);
        stringBuilder.append(prefix).append(separator);
        stringBuilder.append(fileType).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    @Override
    public String builderFilePath(String dirPath, String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if(!StringUtils.isEmpty(dirPath)){
            stringBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     *  上传图片文件
     * @param prefix  文件前缀
     * @param filename  文件名
     * @param inputStream 文件流
     * @return  文件全路径
     */
    @Override
    public String uploadImgFile(String prefix, String uuid,String filename, InputStream inputStream) {
        String filePath = builderFilePathType(prefix, uuid, filename,"image");
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        }catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     *  上传html文件
     * @param prefix  文件前缀
     * @param filename   文件名
     * @param inputStream  文件流
     * @return  文件全路径
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename,InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        }catch (Exception ex){
            log.error("minio put file error.",ex);
            ex.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }
    /**
     * 上传MP4格式的视频文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 视频文件流
     * @return 文件全路径
     */
    @Override
    public String uploadVideoFile(String prefix, String uuid,String filename, InputStream inputStream) {
        String filePath = builderFilePathType(prefix, uuid,filename,"video");
        try {
            // 构建minio上传参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("video/mp4")   // 指定文件类型为MP4
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            // 执行文件上传操作
            minioClient.putObject(putObjectArgs);

            // 构建文件访问url
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator + minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex) {
            log.error("Minio put file error.", ex);
            throw new RuntimeException("上传视频文件失败");
        }
    }

    /**
     * 上传视频文件，并记录原文件格式信息
     *
     * @param prefix          文件前缀
     * @param uuid            UUID
     * @param filename        文件名
     * @param inputStream     视频文件流
     * @param originalFormat  原视频文件格式
     * @return 文件全路径
     */
    @Override
    public String uploadVideoFile(String prefix, String uuid, String filename, InputStream inputStream, String originalFormat) {
        String filePath = builderFilePathType(prefix, uuid, filename, "video");
        try {
            // 构建minio上传参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("video/" + originalFormat) // 使用原文件格式设置contentType
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();

            // 执行文件上传操作
            minioClient.putObject(putObjectArgs);

            // 获取原对象的元数据
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(filePath).build());

            // 获取原文件格式
            String originalContentType = stat.headers().get("Content-Type");

            // 输出元数据信息，方便调试
            log.info("Original Content-Type: {}", originalContentType);

            // 构建文件访问url
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator).append(minIOConfigProperties.getBucket()).append(separator).append(filePath);

            return urlPath.toString();
        } catch (Exception ex) {
            log.error("Minio put file error.", ex);
            throw new RuntimeException("上传视频文件失败");
        }
    }



    /**
     * 删除文件
     * @param pathUrl  文件全路径
     */
    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}",pathUrl);
            e.printStackTrace();
        }
    }


    /**
     * 下载文件
     * @param pathUrl  文件全路径
     * @return  文件流
     */
    @Override
    public OriginalFormatResult downLoadFile(String pathUrl)  {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        InputStream inputStream = null;
        try {
            // 获取对象元数据
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(filePath).build());

            // 获取文件的原格式
            String originalContentType = stat.headers().get("Content-Type");

            // 输出元数据信息，方便调试
            log.info("Original Content-Type: {}", originalContentType);

            // 下载对象
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(filePath).build());

            // 处理文件内容
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                byteArrayOutputStream.write(buff, 0, rc);
            }

//            return byteArrayOutputStream.toByteArray();
            return new OriginalFormatResult(originalContentType, byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            log.error("Minio down file error. pathUrl: {}", pathUrl, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
