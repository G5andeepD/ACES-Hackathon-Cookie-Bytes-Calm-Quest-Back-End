package com.cookiebytes.calmquest.ml;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class EmotionResponseDTO {
    private String status;
    private String emotion;
}
