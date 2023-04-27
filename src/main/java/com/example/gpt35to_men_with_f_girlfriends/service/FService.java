package com.example.gpt35to_men_with_f_girlfriends.service;

import com.example.gpt35to_men_with_f_girlfriends.classifier.dto.request.OpenAIRequestDTO;
import com.example.gpt35to_men_with_f_girlfriends.classifier.dto.response.OpenAIResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FService {

    @Value("${openai.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;




    private String whatShouldISayBuilder(String prompt) {
        String new_prompt = "내 여자친구가 나한테 메신저에 이렇게 말을 했어: \n";
        new_prompt += prompt;
        new_prompt += "\n\n";
        new_prompt += "공감해주고 싶은데 적절한 답변이 뭐가 있어? \n";
        new_prompt += "반말로 공감되는 적절한 답변을 해줘 \n";
        new_prompt += "답변만 알려줘 \n";
        return new_prompt;
    }

    private String rephraseBuilder(String prompt) {
        String new_prompt = "내 여자친구에게 메신저로 얘기를 해주고 싶어. \n";
        new_prompt += prompt;
        new_prompt += "\n\n";
        new_prompt += "이 말을 최대한 자연스럽게, 공감해주는 말로 바꾼 대답을 말해줘 \n";
        new_prompt += "답변만 알려줘 \n";
        return new_prompt;
    }

    public OpenAIResponseDTO ChatGptRequest(String prompt, String Request) throws JsonProcessingException {
        if (Request.equals("whatShouldISay")){
            prompt = whatShouldISayBuilder(prompt);
        } else if (Request.equals("rephrase")){
            prompt = rephraseBuilder(prompt);
        }
        System.out.println("davinci-003 called");
        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);


        int maxTokens = 400;
        double temperature = 0.7;
        OpenAIRequestDTO openAIRequestDTO = new OpenAIRequestDTO(prompt, maxTokens, temperature);
        String payload = objectMapper.writeValueAsString(openAIRequestDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        OpenAIResponseDTO response = objectMapper.readValue(responseEntity.getBody(), OpenAIResponseDTO.class);
        return response;
    }
}
