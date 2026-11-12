package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //查询是否包含菜品（删除确认）
    List<Long> findDish(List<Long> ids);

    //添加套餐菜品信息
    void addSetmealDish(List<SetmealDish> setmealDishes);

    //套餐查询回显
    @Select("select setmeal_dish.* from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getSetmealById(Long id);

    //删除菜品信息
    void deleteSetmeal(List<Long> ids);

    @Select("select sd.name as name,sd.copies as copies,d.image as image,d.description as description from setmeal_dish sd left join dish d on sd.dish_id = d.id where sd.setmeal_id=#{id}")
    List<DishItemVO> getDishItemBySetmealId(Long id);
}
