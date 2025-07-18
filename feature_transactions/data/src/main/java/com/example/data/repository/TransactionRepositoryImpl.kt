package com.example.data.repository

import com.example.core.data.dao.AccountDao
import com.example.core.data.dao.CategoryDao
import com.example.core.data.dao.TransactionDao
import com.example.core.data.entities.TransactionEntity
import com.example.data.api.TransactionApiService
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import com.example.data.mappers.toRequestDto
import com.example.data.models.TransactionRequestDto
import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import android.util.Log

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: TransactionApiService,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao
) : TransactionRepository {

    private fun generateLocalId(): Int = -(System.currentTimeMillis() % Int.MAX_VALUE).toInt()


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
        val request = TransactionRequestDto(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment
        )
        return try {
            val response = apiService.createTransaction(request)
            transactionDao.insert(response.toEntity(
                accountName = account.name,
                categoryName = category.name,
                categoryEmoji = category.emoji,
                isIncome = category.isIncome
            ))
            Result.success(Unit)
        } catch (_: Exception) {
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
            Result.success(Unit)
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Result<Transaction> {
        return try {
            val response = apiService.getTransactionById(transactionId)
            val entity = response.toEntity()
            transactionDao.insert(entity)
            Result.success(entity.toDomain())
        } catch (_: Exception) {
            val entity = transactionDao.getById(transactionId)
                ?: return Result.failure(Exception("Transaction not found"))
            Result.success(entity.toDomain())
        }
    }

    override suspend fun updateTransaction(
        transactionId: Int,
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
        val request = TransactionRequestDto(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            transactionDate = transactionDate,
            comment = comment
        )
        return try {
            val response = apiService.updateTransaction(transactionId, request)
            transactionDao.insert(response.toEntity())
            Result.success(Unit)
        } catch (_: Exception) {
            val oldEntity = transactionDao.getById(transactionId)
                ?: return Result.failure(Exception("Transaction not found"))
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
            Result.success(Unit)
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        return try {
            apiService.deleteTransaction(transactionId)
            val entity = transactionDao.getById(transactionId)
            if (entity != null) {
                transactionDao.insert(
                    entity.copy(isDeleted = true, isSynced = true, lastModified = System.currentTimeMillis())
                )
            }
            Result.success(Unit)
        } catch (_: Exception) {
            val entity = transactionDao.getById(transactionId)
            if (entity != null) {
                transactionDao.insert(
                    entity.copy(isDeleted = true, isSynced = false, lastModified = System.currentTimeMillis())
                )
            }
            Result.success(Unit)
        }
    }


    override suspend fun getExpensesByDate(
        accountId: Int, date: LocalDate
    ): Flow<List<Transaction>> = flow {
        Log.d("TransactionRepository", "getExpensesByDate вызван для accountId=$accountId, date=$date")
        try {
            Log.d("TransactionRepository", "Делаю запрос к apiService.getTransactionsByPeriod(accountId=$accountId, date=$date)")
            val response = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            Log.d("TransactionRepository", "Ответ от сервера: $response")
            val entities = response.map { dto ->
                dto.toEntity()
            }
            Log.d("TransactionRepository", "Загружено с бэка: ${entities.size} транзакций (расходы)")
            transactionDao.insertAll(entities)
        } catch (e: Exception) {
            Log.e("TransactionRepository", "Ошибка загрузки транзакций с бэка (расходы)", e)
        }
        emitAll(
            transactionDao.getByAccountAndDate(
                accountId,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                false
            ).map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
        )
    }

    override suspend fun getIncomesByDate(
        accountId: Int, date: LocalDate
    ): Flow<List<Transaction>> = flow {
        Log.d("TransactionRepository", "getIncomesByDate вызван для accountId=$accountId, date=$date")
        try {
            Log.d("TransactionRepository", "Делаю запрос к apiService.getTransactionsByPeriod(accountId=$accountId, date=$date)")
            val response = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            Log.d("TransactionRepository", "Ответ от сервера: $response")
            val entities = response.map { dto -> dto.toEntity() }
            Log.d("TransactionRepository", "Загружено с бэка: ${entities.size} транзакций (доходы)")
            transactionDao.insertAll(entities)
        } catch (e: Exception) {
            Log.e("TransactionRepository", "Ошибка загрузки транзакций с бэка (доходы)", e)
        }
        emitAll(
            transactionDao.getByAccountAndDate(
                accountId,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                true
            ).map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
        )
    }

    override suspend fun getExpensesByDateRange(
        accountId: Int, startDate: LocalDate, endDate: LocalDate
    ): Flow<List<Transaction>> = flow {
        try {
            val response = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            val entities = response.map { dto -> dto.toEntity() }
            transactionDao.insertAll(entities)
        } catch (e: Exception) {
            // Можно добавить лог ошибки, если нужно
        }
        emitAll(
            transactionDao.getByAccountAndDateRange(
                accountId,
                startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                false
            ).map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
        )
    }

    override suspend fun getIncomesByDateRange(
        accountId: Int, startDate: LocalDate, endDate: LocalDate
    ): Flow<List<Transaction>> = flow {
        try {
            val response = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            val entities = response.map { dto -> dto.toEntity() }
            transactionDao.insertAll(entities)
        } catch (e: Exception) {

        }
        emitAll(
            transactionDao.getByAccountAndDateRange(
                accountId,
                startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                true
            ).map { list -> list.filter { !it.isDeleted }.map { it.toDomain() } }
        )
    }
}
