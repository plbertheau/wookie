package com.plbertheau.data.service

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomEnumDeserializer<T : Enum<T>>(
    private val enumClass: Class<T>,
    private val fallbackEnumValue: T
) : JsonDeserializer<T> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T {
        val jsonValue = json?.asJsonArray

        return enumClass.enumConstants?.firstOrNull {
            it.name.equals(jsonValue)
        } ?: fallbackEnumValue
    }
}