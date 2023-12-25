package com.lijjsk.model.statistics.bos;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class VideoData implements Serializable {
    private Integer videoId;
    /**
     * 总点赞量
     */
    private int sumLike;
    /**
     * 总投币量
     */
    private int sumCoins;
    /**
     * 总收藏量
     */
    private int sumCollect;
    /**
     * 总转发量
     */
    private int sumShare;
    /**
     * 总播放量
     */
    private int sumView;
    /**
     * 总弹幕量
     */
    private int sumDanMu;
    /**
     * 总收入
     */
    private Double income;

}
