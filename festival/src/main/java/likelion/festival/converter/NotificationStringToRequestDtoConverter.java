package likelion.festival.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.festival.dto.NotificationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationStringToRequestDtoConverter extends Throwable implements Converter<String,NotificationDto> {
    private ObjectMapper objectMapper;

    public NotificationStringToRequestDtoConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    NotificationDto notificationDto;

    @Override
    public NotificationDto convert(String source){
        try{
            notificationDto = objectMapper.readValue(source, new TypeReference<NotificationDto>() {
            });
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return notificationDto;
    }
}
