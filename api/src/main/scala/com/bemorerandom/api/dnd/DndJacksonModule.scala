package com.bemorerandom.api.dnd

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{SerializerProvider, JsonSerializer}
import com.fasterxml.jackson.databind.module.SimpleModule

class DndJacksonModule extends SimpleModule {
  addSerializer(new SexSerializer)
}

class SexSerializer extends JsonSerializer[Sex] {
  override def handledType() = classOf[Sex]

  override def serialize(sex: Sex, jgen: JsonGenerator, provider: SerializerProvider): Unit = {
    jgen.writeString(Sex.stringRepresentation(sex))
  }
}
