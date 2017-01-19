package com.power.database;

import java.sql.Types;

/**
 * Created by yu on 2016/12/6.
 */
public class TypeConvert {
    /**
     *
     * @param type
     * @param digits
     * @return
     */
    public static String  sqlTypeToJavaType(int type, int digits) {
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
            case Types.DOUBLE: //8
                dataType = "Double";
                break;
            case Types.FLOAT: //6
                dataType = "Float";
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

    public static String processOracleType(String type){
        return null;
    }
}
