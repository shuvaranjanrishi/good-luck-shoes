package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.SyncLogDao
import com.goodluck.shoes.data.models.SyncAction
import com.goodluck.shoes.data.models.SyncLog
import com.goodluck.shoes.data.models.SyncStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepository @Inject constructor(
    private val syncLogDao: SyncLogDao
) {
    suspend fun addSyncLog(
        entityType: String,
        entityId: Long,
        action: SyncAction,
        payload: String
    ): Long {
        return syncLogDao.insertSyncLog(
            SyncLog(
                entityType = entityType,
                entityId = entityId,
                action = action,
                payload = payload
            )
        )
    }

    suspend fun updateSyncLog(syncLog: SyncLog) {
        syncLogDao.updateSyncLog(syncLog)
    }

    fun getPendingSyncLogs(): Flow<List<SyncLog>> {
        return syncLogDao.getPendingSyncLogs(SyncStatus.PENDING)
    }

    suspend fun getFailedSyncLogsForRetry(limit: Int = 10): List<SyncLog> {
        return syncLogDao.getFailedSyncLogsForRetry(limit)
    }

    suspend fun markSyncAsSuccess(syncLog: SyncLog) {
        syncLogDao.updateSyncLog(syncLog.copy(status = SyncStatus.SUCCESS))
    }

    suspend fun markSyncAsFailed(syncLog: SyncLog, errorMessage: String) {
        syncLogDao.updateSyncLog(
            syncLog.copy(
                status = SyncStatus.FAILED,
                errorMessage = errorMessage,
                retryCount = syncLog.retryCount + 1
            )
        )
    }

    suspend fun deleteOldSuccessfulSyncLogs() {
        syncLogDao.deleteOldSuccessfulSyncLogs()
    }

    fun getSyncLogCount(status: SyncStatus): Flow<Int> {
        return syncLogDao.getSyncLogCount(status)
    }
}
