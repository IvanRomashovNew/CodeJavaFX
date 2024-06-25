package org.example.searadar.mr231.station;

import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.example.searadar.mr231.convert.Mr231Converter;
import ru.oogis.searadar.api.convert.SearadarExchangeConverter;
import ru.oogis.searadar.api.station.AbstractStationType;
import java.nio.charset.Charset;

/**
 *Класс Mr231StationType является частью системы, которая работает с типом станции "МР-231".
 * <p>
 * В классе объявлены приватные константы STATION_TYPE и CODEC_NAME, которые и определяют тип станции и название кодека, соответственно.
 * <p>
 * В классе также есть два метода:
 * - doInitialize() - выполняет инициализацию станции
 * - createConverter() - метод, возвращающий новы объект типа Mr231Converter, предназаначенный для преобразования входящего сообщения или сообщений в объекты типа SearadarStationMessage.
 */

public class Mr231StationType {


    private static final String STATION_TYPE = "МР-231";
    private static final String CODEC_NAME = "mr231";


    protected void doInitialize() {
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(
                Charset.defaultCharset(),
                LineDelimiter.UNIX,
                LineDelimiter.CRLF
        );

    }

    public Mr231Converter createConverter() {
        return new Mr231Converter();
    }
}
