package com.example.data.repository

import com.example.core.data.dao.AccountDao
import com.example.core.data.dao.CategoryDao
import com.example.core.data.dao.TransactionDao
import com.example.core.data.entities.TransactionEntity
import com.example.data.api.TransactionApiService
import com.example.data.mappers.toDomain
import com.example.data.mappers.toRequestDto
import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: TransactionApiService,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao
) : TransactionRepository {

    fun generateLocalId(): Int = -(System.currentTimeMillis() % Int.MAX_VALUE).toInt()

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        val account = accountDao.getById(accountId)
            ?: return Result.failure(Exception("Account not found"))
        val category = categoryDao.getById(categoryId)
            ?: return Result.failure(Exception("Category not found"))

        val entity = TransactionEntity(
            id = generateLocalId(),
            accountId = accountId,
            accountName = account.name,
            categoryId = categoryId,
            categoryName = category.name,
            categoryEmoji = category.emoji,
            isIncome = category.isIncome,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
            isSynced = false,
            isDeleted = false,
            lastModified = System.currentTimeMillis()
        )
        transactionDao.insert(entity)
        return Result.success(Unit)
    }

    override suspend fun getTransactionById(transactionId: Int): Result<Transaction> {
        val entity = transactionDao.getById(transactionId)
            ?: return Result.failure(Exception("Transaction not found"))
        return Result.success(entity.toDomain())
    }

    override suspend fun updateTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        val oldEntity = transactionDao.getById(transactionId)
            ?: return Result.failure(Exception("Transaction not found"))
        val account = accountDao.getById(accountId)
            ?: return Result.failure(Exception("Account not found"))
        val category = categoryDao.getById(categoryId)
            ?: return Result.failure(Exception("Category not found"))

        val updated = oldEntity.copy(
            accountId = accountId,
            accountName = account.name,
            categoryId = categoryId,
            categoryName = category.name,
            categoryEmoji = category.emoji,
            isIncome = category.isIncome,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
            isSynced = false,
            lastModified = System.currentTimeMillis()
        )
        transactionDao.insert(updated)
        return Result.success(Unit)
    }

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        val entity = transactionDao.getById(transactionId)
            ?: return Result.failure(Exception("Transaction not found"))
        transactionDao.insert(
            entity.copy(isDeleted = true, isSynced = false, lastModified = System.currentTimeMillis())
        )
        return Result.success(Unit)
    }

    override suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>> =
        getByAccountAndTypeAndDate(accountId, isIncome = false, date)

    override suspend fun getIncomesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>> =
        getByAccountAndTypeAndDate(accountId, isIncome = true, date)

    private fun getByAccountAndTypeAndDate(
        accountId: Int,
        isIncome: Boolean,
        date: LocalDate
    ): Flow<List<Transaction>> {
        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return transactionDao.getByAccountAndDate(accountId, dateStr, isIncome)
            .map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
    }

    override suspend fun getExpensesByDateRange(
        accountId: Int, startDate: LocalDate, endDate: LocalDate
    ): Flow<List<Transaction>> =
        getByAccountAndTypeAndDateRange(accountId, isIncome = false, startDate, endDate)

    override suspend fun getIncomesByDateRange(
        accountId: Int, startDate: LocalDate, endDate: LocalDate
    ): Flow<List<Transaction>> =
        getByAccountAndTypeAndDateRange(accountId, isIncome = true, startDate, endDate)

    private fun getByAccountAndTypeAndDateRange(
        accountId: Int,
        isIncome: Boolean,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Transaction>> {
        val start = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val end = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return transactionDao.getByAccountAndDateRange(accountId, start, end, isIncome)
            .map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
    }

    suspend fun syncTransactionsWithBackend() {
        val unsynced = transactionDao.getAllUnsynced()
        for (entity in unsynced) {
            try {
                if (entity.isDeleted && entity.id > 0) {
                    apiService.deleteTransaction(entity.id)
                    transactionDao.deleteById(entity.id)
                } else if (entity.id < 0) {
                    val response = apiService.createTransaction(entity.toRequestDto())
                    transactionDao.deleteById(entity.id)
                    transactionDao.insert(entity.copy(
                        id = response.id,
                        isSynced = true,
                        lastModified = System.currentTimeMillis()
                    ))
                } else {
                    apiService.updateTransaction(entity.id, entity.toRequestDto())
                    transactionDao.insert(entity.copy(
                        isSynced = true,
                        lastModified = System.currentTimeMillis()
                    ))
                }
            } catch (_: Exception) {}
        }
        transactionDao.deleteSyncedDeleted()
    }
}
