package com.minio.file.service;

import com.lijjsk.model.common.dtos.OriginalFormatResult;

import java.io.InputStream;

/**
 * @author itheima
 */
public interface FileStorageService {

    public String builderFilePath(String dirPath,String filename);
    public String builderFilePathType(String dirPath,String uuid,String filename,String fileType);
    /**
     *  上传图片文件
     * @param prefix  文件前缀
     * @param filename  文件名
     * @param inputStream 文件流
     * @return  文件全路径
     */
    public String uploadImgFile(String prefix, String uuid,String filename, InputStream inputStream);

    /**
     *  上传html文件
     * @param prefix  文件前缀
     * @param filename   文件名
     * @param inputStream  文件流
     * @return  文件全路径
     */
    public String uploadHtmlFile(String prefix, String filename,InputStream inputStream);
    /**
     * 上传MP4格式的视频文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 视频文件流
     * @return 文件全路径
     */
    public String uploadVideoFile(String prefix, String uuid,String filename, InputStream inputStream);

    /**
     * 上传任意格式的视频文件
     * @param prefix 文件前缀
     * @param uuid 视频uuid
     * @param filename 视频文件名
     * @param inputStream 视频输入流
     * @param originalFormat 视频原格式
     * @return
     */
    public String uploadVideoFile(String prefix, String uuid, String filename, InputStream inputStream, String originalFormat);
    /**
     * 删除文件
     * @param pathUrl  文件全路径
     */
    public void delete(String pathUrl);

    /**
     * 下载文件
     * @param pathUrl  文件全路径
     * @return
     *
     */
    public OriginalFormatResult downLoadFile(String pathUrl);

}
