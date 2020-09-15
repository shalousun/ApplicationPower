package com.power.generator.database;

import java.sql.Types;

/**
 * Created by yu on 2016/12/6.
 */
public class TypeConvert {
    /**
     * @param type
     * @param digits
     * @return
     */
    public static String sqlTypeToJavaType(int type, int digits) {
        String dataType = "";
        switch (type) {
            //12
            case Types.INTEGER:    //4
            case Types.TINYINT:
            case Types.BIT:    //-7
            case Types.SMALLINT:
                dataType = "Integer";
                break;
            case Types.BIGINT: //-5
                dataType = "Long";
                break;
            case Types.DOUBLE: //8
                dataType = "Double";
                break;
            case Types.FLOAT: //6
            case Types.REAL: //7
                dataType = "Float";
                break;
            case Types.DECIMAL:    //3
                dataType = "BigDecimal";
                break;
            case Types.TIME:  //91
                dataType = "Time";
                break;
            case Types.DATE:  //91
            case Types.TIMESTAMP:
                dataType = "Timestamp";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    /**
     * @param type
     * @return
     */
    public static String sqlTypeToJavaType(String type) {
        String dataType = "";
        switch (type) {
            case "number":
            case "numeric":
            case "int":
                dataType = "Integer";
                break;
            case "decimal":
                dataType = "BigDecimal";
                break;
            case "bigint":
                dataType = "Long";
                break;
            case "float":
                dataType = "Float";
                break;
            case "date":
            case "datetime":
            case "timestamp(6)":
            case "timestamp":
                dataType = "Timestamp";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    public static String mybatisType(String type) {
        switch (type) {
            case "Integer":
                return "int";
            case "Long":
                return "long";
            default:
                return "string";
        }
    }
}
