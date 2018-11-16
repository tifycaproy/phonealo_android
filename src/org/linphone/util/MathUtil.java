package org.linphone.util;

import java.math.BigDecimal;

/**
 * Created by Dani on 31/07/2016.
 */
public class MathUtil {

    public static float toBigDecimal(BigDecimal bd) {
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return bd.floatValue();
    }

    public static float setMet(float kmh) {
        float value = 0.0f;
        if(kmh >= 0){
            if (kmh < 4.5f){
                value = 3.3f;
            }else if (kmh < 5.3f) {
                value = 3.8f;
            }else if (kmh < 6.4f) {
                value = 5.0f;
            }else if (kmh < 8.4f) {
                value = 9.0f;
            }else if (kmh < 9.6f) {
                value = 10.0f;
            }else if (kmh < 10.8f) {
                value = 11.0f;
            }else if (kmh < 11.3f) {
                value = 11.5f;
            }else if (kmh < 12.1f) {
                value = 12.5f;
            }else if (kmh < 12.9f) {
                value = 13.5f;
            }else if (kmh < 13.8f) {
                value = 14.0f;
            }else if (kmh < 14.5f) {
                value = 15.0f;
            }else if (kmh < 16.1f) {
                value = 16.0f;
            }else if (kmh < 17.5f) {
                value = 18.0f;
            }else {
                value = 19.0f;
            }
        }
        return value;
    }
}
