//package com.cookiebytes.calmquest.ml;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("api/v1/ml")
//public class MLController {
//    private final MLClient mlClient;
//
//    public MLController(MLClient mlClient) {
//        this.mlClient = mlClient;
//    }
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public EmotionResponseDTO uploadImage(@RequestParam("face") MultipartFile imageFile){
//        return mlClient.getEmotion(imageFile);
//    }
//}
