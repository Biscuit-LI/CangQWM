package com.sky.controller.admin;


import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;


    //分页查询
    @GetMapping("/page")
    public Result<PageResult> getDish(DishPageQueryDTO dish) {
        PageResult pageResult =dishService.getDish(dish);
        return Result.success(pageResult);
    }

    //新增菜品
    @PostMapping
    public Result addDish(@RequestBody DishDTO dish){
        dishService.addDish(dish);
        return Result.success();
    }

    //状态管理
    @PostMapping("/status/{status}")
    public Result updateDishStatus(@PathVariable Integer status,Long id){
        dishService.updateDishStatus(status,id);
        return Result.success();
    }

    //查询回显
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id){
        DishVO dishVO=dishService.getDishById(id);
        return Result.success(dishVO);
    }

    //菜品修改
    @PutMapping
    public Result updateDish(@RequestBody DishDTO dish){
        dishService.updateDish(dish);
        return Result.success();
    }

    //菜品删除
    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        return Result.success();
    }



}
