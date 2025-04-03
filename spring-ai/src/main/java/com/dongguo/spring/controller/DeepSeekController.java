package com.dongguo.spring.controller;

import com.dongguo.spring.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.dongguo.spring.entity.ChatTypeEnum.CHAT;

/**
 * ollama + deepseek
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/deepseek")
public class DeepSeekController {
    /*chatModel方式*/
    private final OllamaChatModel ollamaChatModel;
    private final ChatClient deepSeekChatClient;

    /**
     * 普通对话
     *
     * @param msg
     * @return
     */
    @GetMapping("/modelChat")
    public String modelChat(String msg) {
        return ollamaChatModel.call(msg);
    }


    /**
     * 流式输出
     * produces解决流式输出乱码
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/modelStreamChat", produces = "text/html;charset=utf-8")
    public Flux<String> modelStream(String msg) {
        Prompt prompt = new Prompt(new UserMessage(msg));
        return this.ollamaChatModel.stream(prompt.getContents());
    }


    /*chatClient方式*/

    /**
     * 普通对话
     *
     * @param msg
     * @return
     */
    @GetMapping("clientChat")
    public String clientChat(String msg) {
        return deepSeekChatClient
                .prompt()
                .user(msg)
                .call()
                .content();
    }

    private final ChatHistoryRepository chatHistoryRepository;
    /**
     * 流式输出
     * produces解决流式输出乱码
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/clientStreamChat", produces = "text/html;charset=utf-8")
    public Flux<String> streamChat(String msg, String chatId) {
        // 保存会话id
        chatHistoryRepository.save(CHAT.getType(), chatId);
        // 请求会话
        return deepSeekChatClient
                .prompt()
                .user(msg)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

}