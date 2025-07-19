package com.example.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE accountId = :accountId AND date(transactionDate) = :date AND isIncome = :isIncome AND isDeleted = 0")
    fun getByAccountAndDate(accountId: Int, date: String, isIncome: Boolean): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId AND date(transactionDate) BETWEEN :start AND :end AND isIncome = :isIncome AND isDeleted = 0")
    fun getByAccountAndDateRange(accountId: Int, start: String, end: String, isIncome: Boolean): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE isSynced = 0 OR (isDeleted = 1 AND isSynced = 0)")
    suspend fun getUnsyncedTransactions(): List<TransactionEntity>

    @Query("SELECT COUNT(*) FROM transactions WHERE isSynced = 0 OR (isDeleted = 1 AND isSynced = 0)")
    suspend fun getUnsyncedTransactionsCount(): Int

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Int)
}
