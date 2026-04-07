package usecase

import data.UserPreferences
import model.AuthResponse
import model.SignInParams
import model.UserSettingsData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.UserRepository
import util.Result
import kotlin.getValue

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