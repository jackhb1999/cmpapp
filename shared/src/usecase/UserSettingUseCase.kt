package usecase

import data.UserPreferences
import model.UserSettingsData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserSettingUseCase : KoinComponent {

    private val userPreferences: UserPreferences by inject()

    suspend operator fun invoke(): UserSettingsData? {
        println(18)
        try {
            val userData = userPreferences.getUserData()
            println("21$userData")
            return userData
        }catch (e: Exception){
            println(24)
            e.printStackTrace()
            return null
        }
    }
}