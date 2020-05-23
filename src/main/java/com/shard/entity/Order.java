package com.shard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {

    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private Date startTime;

    @TableField(exist = false)
    private Date endTime;


}
