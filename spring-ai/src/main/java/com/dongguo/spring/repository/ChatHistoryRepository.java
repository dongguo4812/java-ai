package com.dongguo.spring.repository;

import java.util.List;

public interface ChatHistoryRepository {
    /**
     * 保存用户会话记录
     * @param type
     * @param chatId
     */
    void save(String type, String chatId);
    List<String> getChatIds(String type);
}
