package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    PageResult getDish(DishPageQueryDTO dish);

    void addDish(DishDTO dish);

    void updateDishStatus(Integer status,Long id);

    DishVO getDishById(Long id);

    void updateDish(DishDTO dish);

    void deleteDish(List<Long> ids);

}
