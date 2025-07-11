package com.example.core.prefs

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class UserPreferencesImpl @Inject constructor(
    context: Context
) : UserPreferences {

    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun setSelectedAccountId(accountId: Int) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit {
                putInt(KEY_SELECTED_ACCOUNT_ID, accountId)
            }
        }
    }

    override suspend fun getSelectedAccountId(): Int? {
        return withContext(Dispatchers.IO) {
            val accountId = sharedPreferences.getInt(KEY_SELECTED_ACCOUNT_ID, -1)
            if (accountId == -1) null else accountId
        }
    }

    override suspend fun clearSelectedAccount() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .remove(KEY_SELECTED_ACCOUNT_ID)
                .apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "money_talks_prefs"
        private const val KEY_SELECTED_ACCOUNT_ID = "selected_account_id"
    }
} 