package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.service.ShopCartService;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ShopCartMapper {


    List<ShoppingCart> getCartList(ShoppingCart shoppingCart);


    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumber(ShoppingCart cart);

    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            "values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void addCart(ShoppingCart shoppingCart);

//    @Delete("delete from shopping_cart where user_id=#{userId}")
    void clean(ShoppingCart shoppingCart);


//    @Select("select * from shopping_cart where user_id=#{currentId}")
//    List<ShoppingCart> list(Long currentId);
}
