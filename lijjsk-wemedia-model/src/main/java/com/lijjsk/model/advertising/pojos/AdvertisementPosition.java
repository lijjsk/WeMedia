package com.lijjsk.model.advertising.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("advertisement_position")
public class AdvertisementPosition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 广告位id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 广告位剩余数量
     */
    @TableField(value = "num")
    private Integer num;
    /**
     * 广告位名
     */
    @TableField(value = "name")
    private String name;
    /**
     * 广告价格
     */
    @TableField(value = "price")
    private Double price;
    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
