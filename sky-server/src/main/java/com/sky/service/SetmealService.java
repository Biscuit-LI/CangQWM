package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void addSetmeal(SetmealDTO setmealVO);

    PageResult getSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getSetmealById(Long id);

    void updateSetmealStatus(int status,long id);

    void deleteSetmeal(List<Long> ids);

    void updateSetmeal(SetmealDTO setmealDTO);

}
