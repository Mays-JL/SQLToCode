package cn.maysjl.ai.config;

import cn.maysjl.ai.service.SqlToCodeService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: SqlToCode
 * @description:
 * @author: May's_JL
 * @create: 2025-11-27 17:57
 **/
@Component
public class ToolConfig {
    @Bean
    public ToolCallbackProvider myTools(SqlToCodeService service){
        return MethodToolCallbackProvider.builder()
                .toolObjects(service)
                .build();
    }
}
