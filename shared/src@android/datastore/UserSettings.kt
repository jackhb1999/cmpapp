package datastore

import androidx.datastore.core.Serializer
import com.hb.model.UserSettingsData
import java.io.InputStream
import java.io.OutputStream


object UserSettingsSerializer: Serializer<UserSettingsData> {
    override val defaultValue: UserSettingsData
        get() = TODO("Not yet implemented")

    override suspend fun readFrom(input: InputStream): UserSettingsData {
        TODO("Not yet implemented")
    }

    override suspend fun writeTo(t: UserSettingsData, output: OutputStream) {
        TODO("Not yet implemented")
    }

}
