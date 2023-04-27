package com.example.gpt35to_men_with_f_girlfriends.controller;

import com.example.gpt35to_men_with_f_girlfriends.classifier.dto.request.ChatMessageRequestDTO;
import com.example.gpt35to_men_with_f_girlfriends.classifier.dto.response.OpenAIResponseDTO;
import com.example.gpt35to_men_with_f_girlfriends.service.FService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FController {

    private final FService fService;

    /**
     * whatShouldISay method is used to call API based on information from chatMessageRequestDTO.
     * @param chatMessageRequestDTO text : from user input.
     * @throws Exception if chatMessageRequestDTO is null.
     */
    @PostMapping("/toMyGirlfriend")
    public void whatShouldISay(ChatMessageRequestDTO chatMessageRequestDTO) throws Exception {
        OpenAIResponseDTO suggested_response = fService.ChatGptRequest(chatMessageRequestDTO.getText(),"whatShouldISay");
        for (int i = 0; i < suggested_response.getChoices().size(); i++) {
            System.out.println(suggested_response.getChoices().get(i).getText());
        }
    }

    @GetMapping("/rephrase")
    public void rephrase(ChatMessageRequestDTO chatMessageRequestDTO) throws Exception {
//        OpenAIResponseDTO suggested_response = fService.ChatGptRequest(chatMessageRequestDTO.getText(),"rephrase");
        OpenAIResponseDTO suggested_response = fService.ChatGptRequest("여친 배 많이고파?","rephrase");
        for (int i = 0; i < suggested_response.getChoices().size(); i++) {
            System.out.println(suggested_response.getChoices().get(i).getText());
        }
    }

}
