package com.example.starshipShop.mapper.serializer;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.starshipShop.mapper.converter.HashToIdConverter;
import com.example.starshipShop.mapper.converter.IdToHashConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IdCombinedSerializer {
  
  @Value("${app.hashids.salt}")
  private String HASHIDS_SALT_KEY;  
  
  private final IdToHashConverter idToHashConverter;
  private final HashToIdConverter hashToIdConverter;
  
  public class IdSerializer 
  extends JsonSerializer<Long> {
    
    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, 
    SerializerProvider serializerProvider) throws IOException, 
    JsonProcessingException {
      jsonGenerator.writeObject(idToHashConverter.convert(id));
    }
  }
  
  public class IdDeserializer 
  extends JsonDeserializer<Long> {
    
    @Override
    public Long deserialize(JsonParser jsonParser, 
    DeserializationContext deserializationContext)
    throws IOException, JsonProcessingException {
      return hashToIdConverter.convert(jsonParser.getValueAsString());
    }
  }
}