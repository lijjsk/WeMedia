package com.lijjsk.model.advertising.dtos;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class AdvertisementDataDto {
    private Integer advertiserId;
    private Integer impressions;
    private Integer clicks;
    private Double ctr;
    private Integer conversions;
    private Double conversion_Rate;
}
