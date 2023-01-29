package com.starshipshop.inventoryservice.service.mapper.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.starshipshop.inventoryservice.service.mapper.converter.HashToIdConverter;
import com.starshipshop.inventoryservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IdCombinedSerializer {
  
  private final IdToHashConverter idToHashConverter;
  private final HashToIdConverter hashToIdConverter;
  
  public class IdSerializer 
  extends JsonSerializer<Long> {
    
    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeObject(idToHashConverter.convert(id));
    }
  }
  
  public class IdDeserializer extends JsonDeserializer<Long> {
    
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
      return hashToIdConverter.convert(jsonParser.getValueAsString());
    }
  }
}