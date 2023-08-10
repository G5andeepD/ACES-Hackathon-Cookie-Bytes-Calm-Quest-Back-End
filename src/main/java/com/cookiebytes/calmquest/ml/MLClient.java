package com.cookiebytes.calmquest.ml;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MLClient {



        private final RestTemplate restTemplate;

        public MLClient() {
            this.restTemplate = new RestTemplate();
        }

    public EmotionResponseDTO getEmotion(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("face", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<EmotionResponseDTO> responseEntity = restTemplate.exchange(
                    "http://127.0.0.1:8080/emotion",
                    HttpMethod.POST,
                    requestEntity,
                    EmotionResponseDTO.class
            );
            System.out.println(responseEntity.getBody().getEmotion());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Handle error response
                throw new RuntimeException("Failed to fetch data from the API");
            }
        } catch (IOException e) {
            // Handle exception
            throw new RuntimeException("Failed to read image file");
        }
    }

    public StudentFaceEmotion getFullEmotion(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("face", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<StudentFaceEmotion> responseEntity = restTemplate.exchange(
                    "http://127.0.0.1:8080/emotion/full",
                    HttpMethod.POST,
                    requestEntity,
                    StudentFaceEmotion.class
            );
            System.out.println(responseEntity.getBody());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Handle error response
                throw new RuntimeException("Failed to fetch data from the API");
            }
        } catch (IOException e) {
            // Handle exception
            throw new RuntimeException("Failed to read image file");
        }
    }

    public StudentTextEmotion getFullEmotionText(TextSentimentRequest textSentimentRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("text", textSentimentRequest.getText());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<StudentTextEmotion> responseEntity = restTemplate.exchange(
                    "http://127.0.0.1:8080/text/full",
                    HttpMethod.POST,
                    requestEntity,
                    StudentTextEmotion.class
            );
            System.out.println(responseEntity.getBody());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Handle error response
                throw new RuntimeException("Failed to fetch data from the API");
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed due to some reason");
        }
    }

}


