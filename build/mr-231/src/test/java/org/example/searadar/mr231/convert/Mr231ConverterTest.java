package org.example.searadar.mr231.convert;

import org.example.searadar.mr231.station.Mr231StationType;
import org.junit.jupiter.api.Test;
import ru.oogis.searadar.api.message.SearadarStationMessage;
import java.util.List;

class Mr231ConverterTest {

    @Test
    public void convertTTMTest() {
        String mr231_TTM = "$RATTM,66,28.71,341.1,T,57.6,024.5,T,0.4,4.1,N,b,L,,457362,А*42";
        String correctAnswer1 = "TrackedTargetMessage";
        String correctAnswer2 = "targetNumber=66, distance=28.71, bearing=341.1, course=24.5, speed=57.6, type=UNKNOWN, status=LOST, iff=FRIEND}";
        Mr231StationType mr231 = new Mr231StationType();
        Mr231Converter converter = mr231.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_TTM);
        String currentAnswer = "";
        for(SearadarStationMessage message : searadarMessages){
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(correctAnswer1) && currentAnswer.endsWith(correctAnswer2);
    }
    @Test
    public void convertVHWTest() {
        String mr231_VHW = "$RAVHW,115.6,T,,,46.0,N,,*71";
        String correctAnswer1 = "WaterSpeedHeadingMessage";
        String correctAnswer2 = "course=115.6, courseAttr=T, speed=46.0, speedUnit=N}";
        Mr231StationType mr231 = new Mr231StationType();
        Mr231Converter converter = mr231.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_VHW);
        String currentAnswer = "";
        for(SearadarStationMessage message : searadarMessages){
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(correctAnswer1) && currentAnswer.endsWith(correctAnswer2);
    }
    @Test
    public void convertRSDTest() {
        String mr231_RSD = "$RARSD,36.5,331.4,8.4,320.6,,,,,11.6,185.3,96.0,N,N,S*33";
        String correctAnswer1 = "RadarSystemData";
        String correctAnswer2 = "initialDistance=36.5, initialBearing=331.4, movingCircleOfDistance=8.4, bearing=320.6, distanceFromShip=11.6, bearing2=185.3, distanceScale=96.0, distanceUnit=N, displayOrientation=N, workingMode=S}";
        Mr231StationType mr231 = new Mr231StationType();
        Mr231Converter converter = mr231.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_RSD);
        String currentAnswer = "";
        for(SearadarStationMessage message : searadarMessages){
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(correctAnswer1) && currentAnswer.endsWith(correctAnswer2);
    }
    @Test
    public void convertRSDInvalidTest() {
        String mr231_RSD = "$RARSD,14.0,0.0,96.9,306.4,,,,,97.7,11.6,0.3,K,N,S*20";
        String invalidAnswer = "ru.oogis.searadar.api.message.InvalidMessage";
        Mr231StationType mr231 = new Mr231StationType();
        Mr231Converter converter = mr231.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_RSD);
        String currentAnswer = "";
        for(SearadarStationMessage message : searadarMessages){
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(invalidAnswer);
    }
}