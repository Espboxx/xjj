package com.jb.ssologin.dao;

import com.jb.ssologin.vo.DbUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (DbUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-22 20:48:28
 */
public interface DbUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DbUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DbUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dbUser 实例对象
     * @return 对象列表
     */
    List<DbUser> queryAll(DbUser dbUser);

    /**
     * 新增数据
     *
     * @param dbUser 实例对象
     * @return 影响行数
     */
    int insert(DbUser dbUser);

    /**
     * 修改数据
     *
     * @param dbUser 实例对象
     * @return 影响行数
     */
    int update(DbUser dbUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}