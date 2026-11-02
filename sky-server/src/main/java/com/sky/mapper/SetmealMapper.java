package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    //添加套餐
    @AutoFill(OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);

    //分页查询
    Page<SetmealVO> getSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    //查询回显
    @Select("select setmeal.* from setmeal where id=#{id}")
    SetmealVO getSetmealById(Long id);

    //修改菜品状态
    @Update("update setmeal set status=#{status} where id=#{id}")
    void updateSetmealStatus(int status,long id);

    //删除套餐信息
    void deleteSetmeal(List<Long> ids);

    //更新套餐信息
    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);
}
