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
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

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

        String key="dish_"+dish.getCategoryId();
        cleanRedis(key);

        return Result.success();
    }

    //状态管理
    @PostMapping("/status/{status}")
    public Result updateDishStatus(@PathVariable Integer status,Long id){
        dishService.updateDishStatus(status,id);
        cleanRedis("dish_*");
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
        cleanRedis("dish_*");
        return Result.success();
    }

    //菜品删除
    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        cleanRedis("dish_*");
        return Result.success();
    }

    //根据分类查询菜品
    @GetMapping("/list")
    public Result<List<DishVO>> getDishList(@RequestParam String categoryId){
        List<DishVO> dishList=dishService.getDishList(categoryId);
        return Result.success(dishList);
    }

    //清理缓存
    private void cleanRedis(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
