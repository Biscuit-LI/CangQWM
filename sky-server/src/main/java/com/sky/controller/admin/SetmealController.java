package com.sky.controller.admin;


import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;


    //添加套餐
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    //套餐分页查询
    @GetMapping("/page")
    public Result<PageResult> getSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.getSetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    //id查询套餐
    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id) {
        SetmealVO setmealVO=setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }


    //套餐起售状态修改
    @PostMapping("/status/{status}")
    public Result updateSetmealStatus(@PathVariable int status,@RequestParam Long id) {
        setmealService.updateSetmealStatus(status,id);
        return Result.success();
    }

    //删除套餐
    @DeleteMapping
    public Result deleteSetmeal(@RequestParam List<Long> ids) {
        setmealService.deleteSetmeal(ids);
        return Result.success();
    }

    //修改套餐
    @PutMapping
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
}
