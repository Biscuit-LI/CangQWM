package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setMealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //添加套餐主要信息
        setMealMapper.addSetmeal(setmeal);

        //添加套餐菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {setmealDish.setSetmealId(setmeal.getId());});
            setmealDishMapper.addSetmealDish(setmealDishes);
        }
        
    }

    @Override
    public PageResult getSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        Page<SetmealVO> page1=setMealMapper.getSetmeal(setmealPageQueryDTO);
        return new PageResult(page1.getTotal(), page1.getResult());
    }

    @Override
    public SetmealVO getSetmealById(Long id) {
        SetmealVO setmealVO=setMealMapper.getSetmealById(id);
        setmealVO.setSetmealDishes(setmealDishMapper.getSetmealById(id));
        return setmealVO;
    }

    //状态修改
    @Override
    public void updateSetmealStatus(int status,long id) {
        setMealMapper.updateSetmealStatus(status,id);
    }

    //删除
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSetmeal(List<Long> ids) {
        setMealMapper.deleteSetmeal(ids);
        setmealDishMapper.deleteSetmeal(ids);
    }

    //更新
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.updateSetmeal(setmeal);

        setmealDishMapper.deleteSetmeal(Arrays.asList(setmealDTO.getId()));
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {setmealDish.setSetmealId(setmeal.getId());});
            setmealDishMapper.addSetmealDish(setmealDishes);
        }
    }
}
