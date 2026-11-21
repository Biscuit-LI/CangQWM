package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShopCartMapper;
import com.sky.service.ShopCartService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    //添加购物车
    @Override
    public void addCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        //是否已经存在套餐，菜品
        List<ShoppingCart> list=shopCartMapper.getCartList(shoppingCart);

        if(list!=null&&!list.isEmpty()){
            //商品数加1
            ShoppingCart cart=list.get(0);
            cart.setNumber(cart.getNumber()+1);
            shopCartMapper.updateNumber(cart);
        }else{
            if(shoppingCart.getDishId()!=null){
                Dish dish=dishMapper.getDishById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(shoppingCart.getImage());
            }else{
                SetmealVO setmeal=setmealMapper.getSetmealById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shopCartMapper.addCart(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shopCartMapper.getCartList(shoppingCart);
    }

    @Override
    public void clean() {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shopCartMapper.clean(shoppingCart);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list=shopCartMapper.getCartList(shoppingCart);
        if(list!=null&&!list.isEmpty()){
            ShoppingCart cart=list.get(0);
            if(cart.getNumber()==1){
                shopCartMapper.clean(shoppingCart);
            }else{
                cart.setNumber(cart.getNumber()-1);
                shopCartMapper.updateNumber(cart);
            }
        }
    }
}
