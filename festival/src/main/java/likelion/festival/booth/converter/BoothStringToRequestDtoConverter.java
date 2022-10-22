package likelion.festival.booth.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.festival.booth.dto.BoothDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BoothStringToRequestDtoConverter extends Throwable implements Converter<String, BoothDto> {
    private ObjectMapper objectMapper;

    public BoothStringToRequestDtoConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    BoothDto boothDto;

    @Override
    public BoothDto convert(String source){
        try{
            boothDto = objectMapper.readValue(source, new TypeReference<BoothDto>() {
            });
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return boothDto;
    }
}