package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    //分页查询
    @Override
    public PageResult getDish(DishPageQueryDTO dish) {
        PageHelper.startPage(dish.getPage(),dish.getPageSize());
        Page<DishVO> page=dishMapper.getDish(dish);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //新增菜品
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDish(DishDTO dish) {
        Dish dishOne=new Dish();
        BeanUtils.copyProperties(dish,dishOne);
        dishMapper.addDish(dishOne);

        List<DishFlavor> dishFlavor=dish.getFlavors();
        if(dishFlavor!=null&&!dishFlavor.isEmpty()){
            dishFlavor.forEach(d->{
                d.setDishId(dishOne.getId());
            });
            dishFlavorMapper.addDishFlavor(dishFlavor);
        }
    }

    //菜品状态
    @Override
    public void updateDishStatus(Integer status,Long id) {
        dishMapper.updateDishStatus(status,id);
    }

    //回显
    @Override
    public DishVO getDishById(Long id) {
        Dish dish=dishMapper.getDishById(id);

        DishVO dishVO=new DishVO();

        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavorMapper.getDishFlavorById(id));
        return dishVO;
    }

    //更新菜品
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDish(DishDTO dish) {
        Dish dishOne=new Dish();
        BeanUtils.copyProperties(dish,dishOne);
        dishMapper.updateDish(dishOne);

        //删除
        dishFlavorMapper.deleteDishFlavor(Arrays.asList(dishOne.getId()));
        //更新
        List<DishFlavor> dishFlavor=dish.getFlavors();
        if(dishFlavor!=null&&!dishFlavor.isEmpty()){
            dishFlavor.forEach(d->{
                d.setDishId(dishOne.getId());
            });
            dishFlavorMapper.addDishFlavor(dishFlavor);
        }
    }

    //菜品删除
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(List<Long> ids) {
        //查询状态
        for (Long id : ids) {
            Integer status=dishMapper.getStatus(id);
            if(status==0){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //查询是否在套餐内
        List<Long> findId=setmealDishMapper.findDish(ids);
        if(findId!=null&&!findId.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除
        dishMapper.deleteDish(ids);
        dishFlavorMapper.deleteDishFlavor(ids);
    }
}
