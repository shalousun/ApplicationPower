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
            case Types.VARCHAR:  //12
                dataType = "String";
                break;
            case Types.INTEGER:    //4
                dataType = "Integer";
                break;
            case Types.TINYINT:    //4
                dataType = "Integer";
                break;
            case Types.BIT:    //-7
                dataType = "Integer";
                break;
            case Types.BIGINT: //-5
                dataType = "Long";
                break;
            case Types.SMALLINT:
                dataType = "Integer";
                break;
            case Types.DOUBLE: //8
                dataType = "Double";
                break;
            case Types.FLOAT: //6
                dataType = "Float";
                break;
            case Types.REAL: //7
                 dataType= "Float";
                 break;
            case Types.DECIMAL:    //3
                dataType = "BigDecimal";
                break;
            case Types.TIME:  //91
                dataType = "Time";
                break;
            case Types.DATE:  //91
                dataType = "Timestamp";
                break;
            case Types.TIMESTAMP:
                dataType = "Timestamp";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    /**
     *
     * @param type
     * @return
     */
    public static String sqlTypeToJavaType(String type) {
        String dataType = "";
        switch (type){
            case "char":
                dataType = "String";
                break;
            case "varchar2":
                dataType = "String";
                break;
            case "varchar":
                dataType = "String";
                break;
            case "nvarchar":
                dataType = "String";
                break;
            case "nvarchar2":
                dataType = "String";
                break;
            case "number":
                dataType = "Integer";
                break;
            case "numeric":
                dataType = "Integer";
                break;
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
                dataType= "Float";
                break;
            case "date":
                dataType = "Timestamp";
                break;
            case "datetime":
                dataType = "Timestamp";
                break;
            case "timestamp(6)":
                dataType = "Timestamp";
                break;
            case "timestamp":
                dataType = "Timestamp";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }
}
