package com.lijjsk.model.wemedia.comment.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 用户头像
     */
    @TableField(value = "user_profile_photo")
    private String profilePhoto;
    /**
     * 视频id
     */
    @TableField(value = "video_id")
    private Integer videoId;
    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 评论发布时间
     */
    @TableField(value = "created_time")
    private Date createdTime;
    /**
     * 总点赞量
     */
    @TableField(value = "sum_like")
    private Integer sumLike;
    /**
     * 总回复数
     */
    @TableField(value = "sum_reply")
    private Integer sumReply;
    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

}
