package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void addDishFlavor(List<DishFlavor> dishFlavor);

    @Select("select dish_flavor.* from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getDishFlavorById(Long id);


    void deleteDishFlavor(List<Long> ids);
}
