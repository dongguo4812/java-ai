package com.dongguo.spring.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {



    /**
     * 阿里百炼
     *
     * @param dashScopeChatModel
     * @return
     */
    @Bean
    public ChatClient dashScopeChatClient(DashScopeChatModel dashScopeChatModel,VectorStore vectorStore) {
        return ChatClient
                .builder(dashScopeChatModel)
                .defaultSystem("你是东郭商城的客户聊天支持代理，名字叫东郭智能客户，请以友好、乐于助人且愉快的方式来回复，" +
                        "你正在通过在线聊天系统与客户互动。" +
                        "在提供有关取消订单的信息之前，你必须始终从用户处获取以下信息：订单号、客户姓名。" +
                        "在询问用户之前，请检查消息历史记录以获取此信息。" +
                        "在退订之前，请先获取订单信息并且需要用户确认信息，用户回答确认后才进行退订操作。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),    //会话日志
                        new MessageChatMemoryAdvisor(chatMemory()), //会话存储
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build())  //RAG
                )
                .defaultFunctions("cancelOrder","getOrderInfo")  //默认的FunctionCall
                .build();
    }

    /**
     * 内存级别的会话存储 保存到内存中 原理是map
     *
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * deepseek
     *
     * @param ollamaChatModel
     * @return
     */
    @Bean
    public ChatClient deepSeekChatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient
                .builder(ollamaChatModel)
//                .defaultSystem("")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),    //会话日志
                        new MessageChatMemoryAdvisor(chatMemory()))   //会话存储
                .build();
    }

    /**
     * 向量数据库使用内存实现
     * @param embeddingModel
     * @return
     */
    @Bean
    public VectorStore vectorStore(
            @Qualifier("dashscopeEmbeddingModel") EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel)
                .build();
    }

    /**
     * 读取资料转化并写入向量数据库
     * @param embeddingModel
     * @param vectorStore
     * @param termsOfServiceDocs
     * @return
     */
    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore (
            @Qualifier("dashscopeEmbeddingModel") EmbeddingModel embeddingModel,
            VectorStore vectorStore, @Value("classpath:rag/terms-of-service.txt") String termsOfServiceDocs) {
        return args -> {
            vectorStore.write(  //3写入
                    new TokenTextSplitter().transform(   //2切分转化
                            new TextReader(termsOfServiceDocs).read()) //1读取
            );
        };
    }
}