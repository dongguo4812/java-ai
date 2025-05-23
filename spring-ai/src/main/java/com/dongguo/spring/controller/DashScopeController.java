package com.dongguo.spring.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.dongguo.spring.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dongguo.spring.entity.ChatTypeEnum.CHAT;

/**
 * 阿里云百炼  通义千问
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("dashScope")
public class DashScopeController {

    /*chatModel方式*/
    private final DashScopeChatModel dashScopeChatModel;
    private final ChatClient dashScopeChatClient;

    @GetMapping("/modelChat")
    public String modelChat(String msg) {
        /**
         * this.chatclient.prompt():获取一个对话请求构建器，用于构建对话请求。
         * .user(input)：设置用户的输入内容，input参数即为用户提供的文本。
         * .call（):发送构建好的对话请求到服务端，并等待响应。
         * .content（):从响应中提取并返回实际的内容，通常是服务端生成的回复文本。
         */
        return dashScopeChatModel.call(msg);
    }
    /*chatClient方式*/
    private final ChatHistoryRepository chatHistoryRepository;
    @GetMapping("/clientChat")
    public String clientChat(String msg, String chatId) {
        // 保存会话id
        chatHistoryRepository.save(CHAT.getType(), chatId);
        return dashScopeChatClient
                .prompt()
                .user(msg)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .call()
                .content();
    }
}

