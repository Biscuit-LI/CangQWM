package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //查询是否包含菜品（删除确认）
    List<Long> findDish(List<Long> ids);

}
