package likelion.festival.notification.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.festival.notification.dto.NotificationRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationStringToRequestDtoConverter extends Throwable implements
    Converter<String, NotificationRequestDto> {

  private ObjectMapper objectMapper;

  public NotificationStringToRequestDtoConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  NotificationRequestDto notificationRequestDto;

  @Override
  public NotificationRequestDto convert(String source) {
    try {
      notificationRequestDto = objectMapper.readValue(source, new TypeReference<NotificationRequestDto>() {
      });
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return notificationRequestDto;
  }
}