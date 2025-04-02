package com.dongguo.spring.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    /**
     * 阿里百炼
     * @param dashScopeChatModel
     * @return
     */
    @Bean
    public ChatClient dashScopeChatClient(DashScopeChatModel dashScopeChatModel) {
        return ChatClient.builder(dashScopeChatModel).build();
    }

    /**
     * deepseek
     * @param ollamaChatModel
     * @return
     */
    @Bean
    public ChatClient deepSeekChatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }
}
