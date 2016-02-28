package com.bemorerandom.api.xkcd

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

class XkcdJsonModule extends SimpleModule {
  addSerializer(classOf[XkcdRandom], new JsonSerializer[XkcdRandom] {
    override def serialize(value: XkcdRandom, jgen: JsonGenerator, provider: SerializerProvider): Unit = {
      jgen.writeStartObject()
      jgen.writeObjectField("number", value.number)
      jgen.writeEndObject()
    }
  })
}
