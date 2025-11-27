package cn.maysjl.ai.service;

import cn.maysjl.ai.model.SqlToCodeRequest;
import cn.maysjl.ai.model.SqlToCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @program: SqlToCode
 * @description: SqlToCodeService
 * @author: May's_JL
 * @create: 2025-11-27 17:31
 **/
@Slf4j
@Service
public class SqlToCodeService {

    @Tool(description = "根据SQL语句转换成对应的编程代码")
    public SqlToCodeResponse getSqlToCode(SqlToCodeRequest sqlToCodeRequest) {
        log.info("get sqlToCodeRequest :{}",sqlToCodeRequest.toString());

        // 获取对应的编程语言类型 目前只支持java语言
        String codeType = sqlToCodeRequest.getCodeType();

        if (!Objects.equals(codeType, "java")){
            throw new RuntimeException("codeType错误或者暂时不支持该编程语言,当前仅支持Java");
        }

        // 获取对应的sql语句
        String sqlTableName = sqlToCodeRequest.getSqlTableName();
        List<SqlToCodeRequest.SQL> sql = sqlToCodeRequest.getSql();

        if (sqlTableName == null || sqlTableName.isEmpty()){
            throw new RuntimeException("sqlTableName 不能为空");
        }

        if (sql == null || sql.isEmpty()){
            throw new RuntimeException("sql 字段不能为空");
        }


        // 将对应的sql语句编写成代码
        StringBuilder resCode = new StringBuilder();
        resCode.append("import lombok.Data;\n\n");
        resCode.append("@Data\n");
        resCode.append("public class ").append(sqlTableName).append(" {\n\n");

        for (SqlToCodeRequest.SQL SQL : sql){
            String name = SQL.getName();
            String type = sqlToJavaType(SQL.getType());
            resCode.append("    private ").append(type).append(" ").append(name).append(";\n");
        }

        String end = "}";
        resCode.append(end);

        SqlToCodeResponse res = new SqlToCodeResponse();
        res.setResCode(String.valueOf(resCode));
        res.setCodeType("java");

        return res;
    }

    /**
     * SQL 类型 → Java 类型 映射
     */
    private String sqlToJavaType(String sqlType) {
        if (sqlType == null) return "String";

        String t = sqlType.toLowerCase();

        if (t.contains("bigint")) return "Long";
        if (t.contains("int")) return "Integer";
        if (t.contains("char") || t.contains("text") || t.contains("varchar")) return "String";
        if (t.contains("decimal")) return "BigDecimal";
        if (t.contains("datetime") || t.contains("timestamp")) return "LocalDateTime";

        return "String"; // 默认
    }
}
