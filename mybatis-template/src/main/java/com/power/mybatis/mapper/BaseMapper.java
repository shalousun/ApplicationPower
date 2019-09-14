package com.power.mybatis.mapper;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yu 2019/1/5.
 */
public interface BaseMapper<T> {

    /**
     * 保存数据
     *
     * @param entity 数据对象
     * @return int
     */
    int insert(T entity);

    /**
     * 批量添加数据
     *
     * @param entityList 数据对象列表
     * @return int
     */
    int batchInsert(List<T> entityList);

    /**
     * 更新数据
     *
     * @param entity 数据对象
     * @return int
     */
    int update(T entity);

    /**
     * 批量更新
     *
     * @param entityList 数据对象列表
     * @return int
     */
    int batchUpdate(List<T> entityList);

    /**
     * 删除数据
     *
     * @param id id
     * @return int
     */
    int deleteById(Serializable id);

    /**
     * 批量删除
     *
     * @param ids id列表
     * @return int
     */
    int batchDelete(List<? extends Serializable> ids);

    /**
     * 根据 map条件删除记录
     *
     * @param columnMap 查询条件
     * @return int
     */
    int deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据id查询数据
     *
     * @param id 编号
     * @return Object
     */
    T selectById(Serializable id);

    /**
     * 根据map条件查询记录
     *
     * @param columnMap 查询条件
     * @return List
     */
    List<T> selectByMap(Map<String, Object> columnMap);

    /**
     * 根据id来批量查询
     *
     * @param ids ids
     * @return List
     */
    List<T> selectBatchIds(List<? extends Serializable> ids);


    /**
     * 根据map条件查询记录,该方法一般用于分页
     *
     * @param columnMap 查询条件
     * @return List
     */
    List<T> selectPage(Map<String, Object> columnMap);

    /**
     * 查询返回map数据
     *
     * @param columnMap 查询条件
     * @return list
     */
    List<Map<String, Object>> selectMaps(Map<String, Object> columnMap);
}
