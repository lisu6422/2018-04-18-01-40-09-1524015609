package com.example.employee.repository;

import com.example.employee.entity.Company;
import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee
    public Employee findDistinctFirstByName(@Param("name") String name);

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    public Employee findDistinctFirstByNameContainsAndSalaryGreaterThan(@Param("name") String name,@Param("salary") int salary);

    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    public Employee findDistinctFirstByCompanyIdOrderBySalaryDesc(@Param("companyId") long companyId);

    //4.实现对Employee的分页查询，每页两个数据
    //5.查找**的所在的公司的公司名称
    @Query(value = "select c from Employee e,Company c where e.company.id=c.id and e.name = :name")
    public Company findCompanyByEmployeeName(@Param("name") String name);

    //6.将*的名字改成*,输出这次修改影响的行数
    @Modifying
    @Query(value = "update Employee e set e.name = :name where e.name = :oldName")
    public int updateEmployeeNameByName(@Param("oldName") String oldName,@Param("name") String name);

    //7.删除姓名是*的employee
    public int deleteEmployeesByName(@Param("name") String name);

}
