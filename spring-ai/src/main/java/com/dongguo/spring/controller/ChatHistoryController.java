package com.dongguo.spring.controller;

import com.dongguo.spring.entity.MessageVO;
import com.dongguo.spring.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会话历史
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatMemory chatMemory;

    /**
     * 获取会话id
     *
     * @param type
     * @return
     */
    @GetMapping(value = "/{type}")
    public List<String> getHistory(@PathVariable("type") String type) {
        return chatHistoryRepository.getChatIds(type);
    }

    /**
     * 获取指定chatId的对话信息
     *
     * @param type
     * @param chatId
     * @return
     */
    @GetMapping(value = "/chat/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        List<Message> messageList = chatMemory.get(chatId, Integer.MAX_VALUE);
        if (messageList.isEmpty()) {
            return List.of();
        }
//        List<MessageVO> messageVOList = new ArrayList<>(messageList.size());
//        for (Message message : messageList) {
//            MessageVO messageVO = new MessageVO(message);
//            messageVOList.add(messageVO);
//        }

        return messageList.stream().map(MessageVO::new).toList();
    }
}
