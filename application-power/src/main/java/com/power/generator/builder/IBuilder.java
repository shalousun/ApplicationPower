package com.power.generator.builder;

import com.power.generator.database.TableInfo;

/**
 * builder的公用接口,仅用于有表参与的模板生成
 *
 * @author yu 2017/12/29.
 */
public interface IBuilder {

    /**
     * 根据表名和列明生成模板
     *
     * @param tableInfo 表名信息
     * @param columnMap 表对应的数据列
     * @return
     */
    String generateTemplate(TableInfo tableInfo);

}
