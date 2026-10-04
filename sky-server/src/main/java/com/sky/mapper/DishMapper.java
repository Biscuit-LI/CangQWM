package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //分页查询菜品
    Page<DishVO> getDish(DishPageQueryDTO dish);

    //新增菜品
    @AutoFill(OperationType.INSERT)
    void addDish(Dish dishOne);

    //更新菜品状态
    @Update("update dish set status=#{status} where id=#{id}")
    void updateDishStatus(Integer status,Long id);

    //回显
    @Select("select dish.* from dish where id=#{id}")
    Dish getDishById(Long id);

    //更新菜品
    @AutoFill(OperationType.UPDATE)
    void updateDish(Dish dishOne);

    //查询状态
    @Select("select dish.status from dish where id=#{id}")
    Integer getStatus(Long id);

    //删除菜品
    void deleteDish(List<Long> ids);
}
