package com.lijjsk.utils.common;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoSize;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FFmpegUtils {
    /**
     * 通过本地路径获取多媒体文件信息(宽，高，时长，编码等)
     *
     * @param localPath 本地路径
     * @return MultimediaInfo 对象,包含 (宽，高，时长，编码等)
     * @throws EncoderException
     */
    public static MultimediaInfo getMultimediaInfo(String localPath) {
        MultimediaInfo multimediaInfo = null;
        try {
            multimediaInfo = new MultimediaObject(new File(localPath)).getInfo();
        } catch (EncoderException e) {
            System.out.println("获取多媒体文件信息异常");
            e.printStackTrace();
        }
        return multimediaInfo;
    }

    /**
     * 通过URL获取多媒体文件信息
     *
     * @param url 网络url
     * @return MultimediaInfo 对象,包含 (宽，高，时长，编码等)
     * @throws EncoderException
     */
    public static MultimediaInfo getMultimediaInfoFromUrl(String url) {
        MultimediaInfo multimediaInfo = null;
        try {
            multimediaInfo = new MultimediaObject(new URL(url)).getInfo();
        } catch (Exception e) {
            System.out.println("获取多媒体文件信息异常");
            e.printStackTrace();
        }
        return multimediaInfo;
    }

    /**
     * 通过multipartFile获取视频文件，并返回视频信息
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultimediaInfo 对象,包含 (宽，高，时长，编码等)
     */
    public static MultimediaInfo getMultimediaInfoFromMultipartFile(MultipartFile multipartFile) {
        MultimediaInfo multimediaInfo = null;
        try {
            File tempFile = convertMultipartFileToFile(multipartFile,multipartFile.getOriginalFilename());
            multimediaInfo = getMultimediaInfo(tempFile.getAbsolutePath());
            // 删除临时文件
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multimediaInfo;
    }


    /**
     * 通过multipartFile获取视频文件，并将画质压缩为1080p60hz
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultipartFile格式的压缩后的视频文件
     */
    public static MultipartFile compressVideoTo1080p60hz(MultipartFile multipartFile,String fileName) {
        try {
            // 1. 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile,fileName);
            String originalName = multipartFile.getOriginalFilename();
            // 2. 指定压缩后的输出文件
            File outputFile = new File("F:\\video\\videoTemp\\"+"1080p60hz_"+fileName+".mp4");

            // 3. 设置视频属性，包括分辨率、帧率等
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("h264");
            videoAttributes.setBitRate(8000000); // 设置比特率，可以根据需要进行调整
            videoAttributes.setFrameRate(60);
            videoAttributes.setSize(new VideoSize(1920, 1080));

            // 4. 设置音频属性
            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.setCodec("aac");
            audioAttributes.setBitRate(128000); // 设置音频比特率，可以根据需要进行调整
            audioAttributes.setChannels(2);
            audioAttributes.setSamplingRate(44100);

            // 5. 创建 MultimediaObject，进行视频压缩
            MultimediaObject multimediaObject = new MultimediaObject(inputFile);
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setVideoAttributes(videoAttributes);
            //不压缩音频
            encodingAttributes.setAudioAttributes(audioAttributes);

            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, outputFile, encodingAttributes);

            // 6. 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(outputFile);

            // 7. 删除临时文件
            inputFile.delete();
            outputFile.delete();

            return compressedMultipartFile;
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过multipartFile获取视频文件，并将画质压缩为1080p30hz
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultipartFile格式的压缩后的视频文件
     */
    public static MultipartFile compressVideoTo1080p30hz(MultipartFile multipartFile,String fileName) {
        try {
            // 1. 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile,fileName);
            String originalName = multipartFile.getOriginalFilename();
            // 2. 指定压缩后的输出文件
            File outputFile = new File("F:\\video\\videoTemp\\"+"1080p30hz_"+fileName+".mp4");

            // 3. 设置视频属性，包括分辨率、帧率等
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("h264");
            videoAttributes.setBitRate(5000000); // 设置比特率，可以根据需要进行调整
            videoAttributes.setFrameRate(60);
            videoAttributes.setSize(new VideoSize(1920, 1080));

            // 5. 创建 MultimediaObject，进行视频压缩
            MultimediaObject multimediaObject = new MultimediaObject(inputFile);
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setVideoAttributes(videoAttributes);
            //不压缩音频
            encodingAttributes.setAudioAttributes(null);

            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, outputFile, encodingAttributes);

            // 6. 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(outputFile);

            // 7. 删除临时文件
            inputFile.delete();
            outputFile.delete();

            return compressedMultipartFile;
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过multipartFile获取视频文件，并将画质压缩为720p30hz
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultipartFile格式的压缩后的视频文件
     */
    public static MultipartFile compressVideoTo720p30hz(MultipartFile multipartFile,String fileName) {
        try {
            // 1. 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile,fileName);
            String originalName = multipartFile.getOriginalFilename();
            // 2. 指定压缩后的输出文件
            File outputFile = new File("F:\\video\\videoTemp\\"+"720p30hz_"+fileName+".mp4");

            // 3. 设置视频属性，包括分辨率、帧率等
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("h264");
            videoAttributes.setBitRate(3000000); // 设置比特率，可以根据需要进行调整
            videoAttributes.setFrameRate(30);
            videoAttributes.setSize(new VideoSize(1280, 720));

            // 5. 创建 MultimediaObject，进行视频压缩
            MultimediaObject multimediaObject = new MultimediaObject(inputFile);
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setVideoAttributes(videoAttributes);
            //不压缩音频
            encodingAttributes.setAudioAttributes(null);

            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, outputFile, encodingAttributes);

            // 6. 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(outputFile);

            // 7. 删除临时文件
            inputFile.delete();
            outputFile.delete();

            return compressedMultipartFile;
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过multipartFile获取视频文件，并将画质压缩为480p30hz
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultipartFile格式的压缩后的视频文件
     */
    public static MultipartFile compressVideoTo480p30hz(MultipartFile multipartFile,String fileName) {
        try {
            // 1. 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile,fileName);
            String originalName = multipartFile.getOriginalFilename();
            // 2. 指定压缩后的输出文件
            File outputFile = new File("F:\\video\\videoTemp\\"+"480p30hz_"+fileName+".mp4");

            // 3. 设置视频属性，包括分辨率、帧率等
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("h264");
            videoAttributes.setBitRate(1500000); // 设置比特率，可以根据需要进行调整
            videoAttributes.setFrameRate(30);
            videoAttributes.setSize(new VideoSize(854, 480));

            // 5. 创建 MultimediaObject，进行视频压缩
            MultimediaObject multimediaObject = new MultimediaObject(inputFile);
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setVideoAttributes(videoAttributes);
            //不压缩音频
            encodingAttributes.setAudioAttributes(null);

            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, outputFile, encodingAttributes);

            // 6. 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(outputFile);

            // 7. 删除临时文件
            inputFile.delete();
            outputFile.delete();

            return compressedMultipartFile;
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 通过multipartFile获取视频文件，并将画质压缩为320p30hz
     *
     * @param multipartFile multipartFile类型视频参数
     * @return MultipartFile格式的压缩后的视频文件
     */
    public static MultipartFile compressVideoTo320p30hz(MultipartFile multipartFile,String fileName) {
        try {
            // 1. 将 MultipartFile 转换为临时文件
            File inputFile = convertMultipartFileToFile(multipartFile,fileName);
            String originalName = multipartFile.getOriginalFilename();
            // 2. 指定压缩后的输出文件
            File outputFile = new File("F:\\video\\videoTemp\\"+"320p30hz_"+fileName+".mp4");

            // 3. 设置视频属性，包括分辨率、帧率等
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("h264");
            videoAttributes.setBitRate(1000000); // 设置比特率，可以根据需要进行调整
            videoAttributes.setFrameRate(30);
            videoAttributes.setSize(new VideoSize(640, 320));

            // 5. 创建 MultimediaObject，进行视频压缩
            MultimediaObject multimediaObject = new MultimediaObject(inputFile);
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setVideoAttributes(videoAttributes);
            //不压缩音频
            encodingAttributes.setAudioAttributes(null);

            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, outputFile, encodingAttributes);

            // 6. 将压缩后的文件转换为 MultipartFile
            MultipartFile compressedMultipartFile = convertFileToMultipartFile(outputFile);

            // 7. 删除临时文件
            inputFile.delete();
            outputFile.delete();

            return compressedMultipartFile;
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 这个方法用于将 MultipartFile 转换为临时文件，具体实现可以根据你的需求进行调整
    private static File convertMultipartFileToFile(MultipartFile multipartFile,String fileName) throws IOException {
        File file = new File("F:\\video\\videoTemp\\"+"uncompressed_"+fileName+".mp4");
        if (file.exists()) {
            // 文件已存在，可能需要进行处理
        } else {
            // 文件不存在，创建新文件
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        return file;
    }

    // 这个方法用于将 File 转换为 MultipartFile
    private static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        return new MockMultipartFile("file", file.getName(), null, fileBytes);
    }

    /**
     * 获取MultipartFile类型视频文件的缩略图
     * @param multipartFile  multipartFile类型视频参数
     * @return
     */

    public static MultipartFile getThumbnailFromMultipartFile(MultipartFile multipartFile) {
        try {
            File tempFile = convertMultipartFileToFile(multipartFile,multipartFile.getOriginalFilename());
            String filePath = "F:\\video\\videoTemp\\default.jpg";
            getTargetThumbnail(tempFile.getAbsolutePath(), filePath);
            File Thumbnail = new File(filePath);
            MultipartFile image = convertFileToMultipartFile(Thumbnail);
            // 删除临时文件
            tempFile.delete();
            Thumbnail.delete();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final int SAMPLING_RATE = 16000;
    private static final int SINGLE_CHANNEL = 1;

    /**
     * 音频格式化为wav,并设置单声道和采样率
     *
     * @param url 需要转格式的音频
     * @param targetPath 格式化后要保存的目标路径
     */
    public static boolean formatAudio(String url, String targetPath) {
        File target = new File(targetPath);
        MultimediaObject multimediaObject;
        try {
            // 若是本地文件： multimediaObject = new MultimediaObject(new File("你的本地路径"));
            multimediaObject = new MultimediaObject(new URL(url));
            // 音频参数
            // TODO: 2023/1/31 此处按需自定义音频参数
            AudioAttributes audio = new AudioAttributes();
            // 采样率
            audio.setSamplingRate(SAMPLING_RATE);
            // 单声道
            audio.setChannels(SINGLE_CHANNEL);
            Encoder encoder = new Encoder();
            EncodingAttributes attrs = new EncodingAttributes();
            // 输出格式
            attrs.setOutputFormat("wav");
            attrs.setAudioAttributes(audio);
            encoder.encode(multimediaObject, target, attrs);
            return true;
        } catch (Exception e) {
            System.out.println("格式化音频异常");
            return false;
        }
    }

    /**
     * 视频格式化为mp4
     *
     * @param url
     * @param targetPath
     * @return
     */
    public static boolean formatToMp4(String url, String targetPath) {
        File target = new File(targetPath);
        MultimediaObject multimediaObject;
        try {
            // 若是本地文件： multimediaObject = new MultimediaObject(new File("你的本地路径"));
            multimediaObject = new MultimediaObject(new URL(url));
            EncodingAttributes attributes = new EncodingAttributes();
            // 设置视频的音频参数
            AudioAttributes audioAttributes = new AudioAttributes();
            attributes.setAudioAttributes(audioAttributes);
            // 设置视频的视频参数
            VideoAttributes videoAttributes = new VideoAttributes();
            // 设置帧率
            videoAttributes.setFrameRate(25);
            attributes.setVideoAttributes(videoAttributes);
            // 设置输出格式
            attributes.setOutputFormat("mp4");
            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, target, attributes);
            return true;
        } catch (Exception e) {
            System.out.println("格式化视频异常");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取视频缩略图 获取视频第0秒的第一帧图片
     *
     * <p>执行的ffmpeg 命令为： ffmpeg -i 你的视频文件路径 -ss 指定的秒数 生成文件的全路径地址
     *
     * @param localPath 本地路径
     * @param targetPath 存放的目标路径
     * @return
     */
    public static boolean getTargetThumbnail(String localPath, String targetPath) {
        // FIXME: 2023/1/31  该方法基本可作为执行ffmpeg命令的模板方法，之后的几个方法与此类似
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            //指定输入文件
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(localPath);
            //指定缩略图的时间偏移
            ffmpeg.addArgument("-ss");
            // 此处可自定义视频的秒数
            ffmpeg.addArgument("0");
            // 设置缩略图的视频质量
            ffmpeg.addArgument("-q:v");
            ffmpeg.addArgument(String.valueOf(30));
            // 设置只截取第一帧
            ffmpeg.addArgument("-vframes");
            ffmpeg.addArgument("1");
            //指定输出路径
            ffmpeg.addArgument(targetPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
        } catch (IOException e) {
            System.out.println("获取视频缩略图失败");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 等待命令执行成功，退出
     *
     * @param br
     * @throws IOException
     */
    private static void blockFfmpeg(BufferedReader br) throws IOException {
        String line;
        // 该方法阻塞线程，直至合成成功
        while ((line = br.readLine()) != null) {
            doNothing(line);
        }
    }

    /**
     * 打印日志
     *
     * @param line
     */
    private static void doNothing(String line) {
        // FIXME: 2023/1/31 正式使用时注释掉此行，仅用于观察日志
        System.out.println(line);
    }

    /**
     * 视频增加字幕
     *
     * @param originVideoPath 原视频地址
     * @param targetVideoPath 目标视频地址
     * @param srtPath 固定格式的srt文件地址或存储位置，字母文件名： xxx.srt，样例看博客
     * @return
     * @throws Exception
     */
    public static boolean addSubtitle(
            String originVideoPath, String srtPath, String targetVideoPath) {
        try {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(originVideoPath);
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(srtPath);
            ffmpeg.addArgument("-c");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument(targetVideoPath);
            ffmpeg.execute();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }
        } catch (IOException e) {
            System.out.println("字幕增加失败");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 常用命令
     *
     * @return
     */
    public static void cmd() {
        // FIXME: 2023/1/31  还有很多类似命令 不再一一列举 ，附上命令,具体写法参考 getTargetThumbnail或addSubtitle方法
        // FIXME: 2023/1/31 ffmpeg命令网上搜索即可

        // 剪切视频
        // ffmpeg -ss 00:00:00 -t 00:00:30 -i test.mp4 -vcodec copy -acodec copy output.mp4
        // * -ss 指定从什么时间开始
        // * -t 指定需要截取多长时间
        // * -i 指定输入文件

        // ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy cut.mp4
        // ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy -avoid_negative_ts 1 cut.mp4

        // 拼接MP4
        // 第一种方法：
        // ffmpeg -i "concat:1.mp4|2.mp4|3.mp4" -codec copy out_mp4.mp4
        // 1.mp4 第一个视频文件的全路径
        // 2.mp4 第二个视频文件的全路径

        // 提取视频中的音频
        // ffmpeg -i input.mp4 -acodec copy -vn output.mp3
        // -vn: 去掉视频；-acodec: 音频选项， 一般后面加copy表示拷贝

        // 音视频合成
        // ffmpeg -y –i input.mp4 –i input.mp3 –vcodec copy –acodec copy output.mp4
        // -y 覆盖输出文件

        // 剪切视频
        //  ffmpeg -ss 0:1:30 -t 0:0:20 -i input.mp4 -vcodec copy -acodec copy output.mp4
        // -ss 开始时间; -t 持续时间

        // 视频截图
        //  ffmpeg –i test.mp4 –f image2 -t 0.001 -s 320x240 image-%3d.jpg
        // -s 设置分辨率; -f 强迫采用格式fmt;

        // 视频分解为图片
        //   ffmpeg –i test.mp4 –r 1 –f image2 image-%3d.jpg
        // -r 指定截屏频率

        // 将图片合成视频
        //  ffmpeg -f image2 -i image%d.jpg output.mp4

        // 视频拼接
        //  ffmpeg -f concat -i filelist.txt -c copy output.mp4

        // 将视频转为gif
        //    ffmpeg -i input.mp4 -ss 0:0:30 -t 10 -s 320x240 -pix_fmt rgb24 output.gif
        // -pix_fmt 指定编码

        // 视频添加水印
        //  ffmpeg -i input.mp4 -i logo.jpg
        // -filter_complex[0:v][1:v]overlay=main_w-overlay_w-10:main_h-overlay_h-10[out] -map [out] -map
        // 0:a -codec:a copy output.mp4
        // main_w-overlay_w-10 视频的宽度-水印的宽度-水印边距；

    }
}
