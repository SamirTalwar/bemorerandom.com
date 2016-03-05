package com.bemorerandom.api

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}

class SymbolTupleJacksonModule extends SimpleModule {
  addSerializer(new SymbolTupleSerializer)

  class SymbolTupleSerializer[T] extends JsonSerializer[(Symbol, T)] {
    override def handledType() = classOf[(Symbol, T)]

    override def serialize(tuple: (Symbol, T), jgen: JsonGenerator, provider: SerializerProvider): Unit =
      tuple match { case (key, value) =>
        jgen.writeStartObject()
        provider.defaultSerializeField(key.name, value, jgen)
        jgen.writeEndObject()
      }
  }
}
