//package com.cookiebytes.calmquest.ml;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.MediaType;
//import org.springframework.http.HttpEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//public class MLClient {
//
//
//
//        private final RestTemplate restTemplate;
//
//        public MLClient() {
//            this.restTemplate = new RestTemplate();
//        }
//
//    public EmotionResponseDTO getEmotion(MultipartFile image) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("image", new ByteArrayResource(image.getBytes()) {
//                @Override
//                public String getFilename() {
//                    return image.getOriginalFilename();
//                }
//            });
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<EmotionResponseDTO> responseEntity = restTemplate.exchange(
//                    "http://192.168.1.11:5000/emotion",
//                    HttpMethod.POST,
//                    requestEntity,
//                    EmotionResponseDTO.class
//            );
//
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                return responseEntity.getBody();
//            } else {
//                // Handle error response
//                throw new RuntimeException("Failed to fetch data from the API");
//            }
//        } catch (IOException e) {
//            // Handle exception
//            throw new RuntimeException("Failed to read image file");
//        }
//    }
//
//}
//
//
