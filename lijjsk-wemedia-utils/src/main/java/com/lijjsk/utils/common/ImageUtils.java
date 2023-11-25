package com.lijjsk.utils.common;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 压缩 MultipartFile 类型的图像
     *
     * @param multipartFile  MultipartFile 类型的图像参数
     * @param quality        图像质量，范围通常是 0.0 到 1.0
     * @param maxWidth       压缩后的图像最大宽度
     * @param maxHeight      压缩后的图像最大高度
     * @return 压缩后的图像的 MultipartFile
     */
    public static MultipartFile compressImage(MultipartFile multipartFile, float quality, int maxWidth, int maxHeight) {
        try {
            // 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile);

            // 压缩图像并保存到临时路径
            File compressedFile = new File("F:\\video\\imageTemp\\compressed_temp_image.jpg");
            Thumbnails.of(inputFile)
                    .size(maxWidth, maxHeight)
                    .outputQuality(quality)
                    .toFile(compressedFile);

            // 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(compressedFile);

            // 删除临时文件
            inputFile.delete();
            compressedFile.delete();

            // 返回压缩后的 MultipartFile
            return compressedMultipartFile;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将 MultipartFile 转换为临时文件
    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File("F:\\video\\imageTemp\\uncompressed_temp_image.jpg");
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    // 将 File 转换为 MultipartFile
    private static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile("file", file.getName(), "image/jpeg", input);
        }
    }
}
