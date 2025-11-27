package cn.maysjl.ai.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * @program: SqlToCode
 * @description: SqlToCode结果响应对象
 * @author: May's_JL
 * @create: 2025-11-27 17:27
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SqlToCodeResponse {

    @JsonProperty(value = "codeType")
    @JsonPropertyDescription("编程语言")
    private String codeType;

    @JsonProperty(value = "resCode")
    @JsonPropertyDescription("结果代码")
    private String resCode;
}
