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
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量添加数据
     * @param entityList
     * @return
     */
    int batchInsert(List<T> entityList);

    /**
     * 更新数据
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 批量更新
     * @param entityList
     * @return
     */
    int batchUpdate(List<T> entityList);
    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(Serializable id);

    /**
     * 根据 map条件删除记录
     * @param columnMap
     * @return int
     */
    int deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    T selectById(Serializable id);

    /**
     * 根据map条件查询记录
     * @param columnMap
     * @return
     */
    List<T> selectByMap(Map<String, Object> columnMap);


    /**
     * 根据map条件查询记录,该方法一般用于分页
     * @param columnMap
     * @return
     */
    List<T> selectPage(Map<String, Object> columnMap);

    /**
     * 查询返回map数据
     * @param columnMap
     * @return
     */
    List<Map<String,Object>> selectMaps(Map<String, Object> columnMap);
}
