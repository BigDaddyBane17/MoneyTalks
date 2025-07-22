package com.example.core.prefs

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.example.core.domain.models.Account
import com.google.gson.Gson

@Singleton
class UserPreferencesImpl @Inject constructor(
    context: Context
) : UserPreferences {

    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

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

    override suspend fun setSelectedAccount(account: Account) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(account)
            sharedPreferences.edit {
                putString(KEY_SELECTED_ACCOUNT_JSON, json)
            }
        }
    }

    override suspend fun getSelectedAccount(): Account? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_SELECTED_ACCOUNT_JSON, null)
            if (json != null) gson.fromJson(json, Account::class.java) else null
        }
    }

    companion object {
        private const val PREFS_NAME = "money_talks_prefs"
        private const val KEY_SELECTED_ACCOUNT_ID = "selected_account_id"
        private const val KEY_SELECTED_ACCOUNT_JSON = "selected_account_json"
    }
} 