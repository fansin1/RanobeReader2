package org.fansin.ranobereader.domain.model

import com.google.gson.*
import java.lang.Exception
import java.lang.reflect.Type

class NovelDeserializer : JsonDeserializer<Novel> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Novel {

        val jsonObject = json.asJsonObject

        return Novel(
            jsonObject["id"].asInt,
            orDefault("", jsonObject["title"]::getAsString),
            orDefault("") {
                orDefault(null, jsonObject["author"]::getAsJsonObject)
                    ?.get("name")?.asString ?: ""
            },
            orDefault(JsonArray(), jsonObject["genres"]::getAsJsonArray).map {
                it.asJsonObject["title"].asString
            },
            orDefault(JsonArray(), jsonObject["chapters"]::getAsJsonArray).map {
                Chapter(
                    jsonObject["id"].asInt,
                    jsonObject["title"].asString
                )
            }.toMutableList(),
            orDefault("", { jsonObject["images"]
                .asJsonArray[0].asJsonObject["url"].asString }),
            jsonObject["description"].asString
        )
    }

    private fun<T> orDefault(default: T, valueGetter: () -> T): T {
        return try {
            valueGetter()
        } catch (e: Exception) {
            default
        }
    }
}