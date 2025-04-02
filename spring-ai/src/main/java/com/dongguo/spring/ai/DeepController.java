package com.dongguo.spring.ai;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ollama + deepseek
 */
@RestController
@RequestMapping("ds")
public class DeepController {

    private final OllamaChatModel ollamaChatModel;

    @Autowired
    public DeepController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    /**
     * 普通对话
     *
     * @param msg
     * @return
     */
    @GetMapping("normal/{msg}")
    public String normalChat(@PathVariable("msg") String msg) {
        return ollamaChatModel.call(msg);
    }

    /**
     * 流式输出
     * produces解决流式输出乱码
     * @param msg
     * @return
     */
    @GetMapping(value = "/stream/{msg}", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@PathVariable(value = "msg") String msg) {
        Prompt prompt = new Prompt(new UserMessage(msg));
        return this.ollamaChatModel.stream(prompt.getContents());
    }
}