package com.lijjsk.model.advertising.dtos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PositionDto {
    private Integer num;
    private String name;
    private Double price;
}
