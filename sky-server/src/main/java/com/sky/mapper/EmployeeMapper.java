package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    List<Employee> getEmployeeByPage(String name);

    //保存员工数据
    @AutoFill(OperationType.INSERT)
    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(Employee employee);

    //员工状态管理（是否禁用）
    //更新员工信息
    @AutoFill(OperationType.UPDATE)
    void updateEmployee(Employee employee);

    //id查询员工信息
    @Select("select employee.* from employee where id=#{id}")
    Employee getEmployeeById(int id);

}
