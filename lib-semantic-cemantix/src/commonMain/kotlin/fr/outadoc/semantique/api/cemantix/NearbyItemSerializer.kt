package fr.outadoc.semantique.api.cemantix

import fr.outadoc.semantique.api.cemantix.model.NearbyItem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object NearbyItemSerializer : KSerializer<NearbyItem> {

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = listSerialDescriptor<String>()

    override fun serialize(encoder: Encoder, value: NearbyItem) = TODO()

    override fun deserialize(decoder: Decoder): NearbyItem {
        require(decoder is JsonDecoder)

        val array = decoder.decodeJsonElement().jsonArray
        return NearbyItem(
            word = array[0].jsonPrimitive.content,
            percentile = array[1].jsonPrimitive.int,
            score = array[2].jsonPrimitive.double
        )
    }
}
