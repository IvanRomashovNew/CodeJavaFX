package org.example.searadar.mr231_3.convert;

import org.example.searadar.mr231_3.station.Mr231_3StationType;
import org.junit.Test;
import ru.oogis.searadar.api.message.SearadarStationMessage;
import java.util.List;

public class Mr231_3ConverterTest {

    @Test
    public void convertTTMTest() {
        String mr231_3_TTM = "$RATTM,23,13.88,137.2,T,63.8,094.3,T,9.2,79.4,N,b,T,,783344,А*42";
        String correctAnswer1 = "TrackedTargetMessage";
        String correctAnswer2 = "targetNumber=23, distance=13.88, bearing=137.2, course=94.3, speed=63.8, type=UNKNOWN, status=TRACKED, iff=FRIEND}";
        Mr231_3StationType mr231_3 = new Mr231_3StationType();
        Mr231_3Converter converter = mr231_3.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_3_TTM);
        String currentAnswer = "";
        for (SearadarStationMessage message : searadarMessages) {
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(correctAnswer1) && currentAnswer.endsWith(correctAnswer2);
    }

    @Test
    public void convertRSDTest() {
        String mr231_3_RSD = "$RARSD,50.5,309.9,64.8,132.3,,,,,52.6,155.0,48.0,K,N,S*28";
        String correctAnswer1 = "RadarSystemData";
        String correctAnswer2 = "initialDistance=50.5, initialBearing=309.9, movingCircleOfDistance=64.8, bearing=132.3, distanceFromShip=52.6, bearing2=155.0, distanceScale=48.0, distanceUnit=K, displayOrientation=N, workingMode=S}";
        Mr231_3StationType mr231_3 = new Mr231_3StationType();
        Mr231_3Converter converter = mr231_3.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_3_RSD);
        String currentAnswer = "";
        for (SearadarStationMessage message : searadarMessages) {
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(correctAnswer1) && currentAnswer.endsWith(correctAnswer2);
    }

    @Test
    public void convertRSDInvalidTest() {
        String mr231_3_RSD = "$RARSD,14.0,0.0,96.9,306.4,,,,,97.7,11.6,0.3,K,N,S*20";
        String invalidAnswer = "ru.oogis.searadar.api.message.InvalidMessage";
        Mr231_3StationType mr231_3 = new Mr231_3StationType();
        Mr231_3Converter converter = mr231_3.createConverter();
        List<SearadarStationMessage> searadarMessages = converter.convert(mr231_3_RSD);
        String currentAnswer = "";
        for (SearadarStationMessage message : searadarMessages) {
            currentAnswer = message.toString();
        }
        assert currentAnswer.startsWith(invalidAnswer);
    }
}