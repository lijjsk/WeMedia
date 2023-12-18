package com.lijjsk.model.wemedia.user.pojos;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "identity")
public class Identity implements Serializable {
    @TableId(value = "id")
    private Integer id;
    private String name;
}
