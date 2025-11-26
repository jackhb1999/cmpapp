package datastore

import androidx.datastore.core.Serializer
import com.hb.model.UserSettingsData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object UserSettingsSerializer : Serializer<UserSettingsData> {
    override val defaultValue: UserSettingsData
        get() = UserSettingsData()

    override suspend fun readFrom(input: InputStream): UserSettingsData {
        return try {
            Json.decodeFromString(
                deserializer = UserSettingsData.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSettingsData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserSettingsData.serializer(),
                value = t,
            ).encodeToByteArray()
        )
    }

}
