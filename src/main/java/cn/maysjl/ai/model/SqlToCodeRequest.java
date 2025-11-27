package cn.maysjl.ai.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

/**
 * @program: SqlToCode
 * @description: SqlToCode请求对象
 * @author: May's_JL
 * @create: 2025-11-27 17:12
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SqlToCodeRequest {

    @JsonProperty(required = true, value = "codeType")
    @JsonPropertyDescription("转换成对应的编程语言名称，如果是大写的先转换为小写，例如Java:java")
    private String codeType;

    @JsonProperty(required = true, value = "sqlTableName")
    @JsonPropertyDescription("提取数据库表的表名信息，例如CREATE TABLE product (\n" +
            "  id BIGINT,\n" +
            "  name VARCHAR(100),\n" +
            "  price INT\n" +
            ");\n:product")
    private String sqlTableName;

    /**
     * CREATE TABLE product (
     *   id BIGINT,
     *   name VARCHAR(100),
     *   price INT
     * );
     */
    @JsonProperty(required = true, value = "sql")
    @JsonPropertyDescription("字段信息,如果是复杂的sql请先转换为简短的sql保留最基本的字段和类型,例如CREATE TABLE product (\n" +
            "  id BIGINT,\n" +
            "  name VARCHAR(100),\n" +
            "  price INT\n" +
            ");\n:{{name:id,type:BIGINT},{name:name,type:VARCHAR(100)},{name:price,type:INT}}")
    private List<SQL> sql;


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SQL{
        @JsonProperty(required = true, value = "name")
        @JsonPropertyDescription("字段名称")
        private String name;

        @JsonProperty(required = true, value = "type")
        @JsonPropertyDescription("字段类型")
        private String type;
    }
}
