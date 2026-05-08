package com.goodluck.shoes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.goodluck.shoes.data.models.SyncLog
import com.goodluck.shoes.data.models.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncLogDao {
    @Insert
    suspend fun insertSyncLog(syncLog: SyncLog): Long

    @Update
    suspend fun updateSyncLog(syncLog: SyncLog)

    @Delete
    suspend fun deleteSyncLog(syncLog: SyncLog)

    @Query("SELECT * FROM sync_logs WHERE status = :status ORDER BY createdAt DESC")
    fun getPendingSyncLogs(status: SyncStatus): Flow<List<SyncLog>>

    @Query("SELECT * FROM sync_logs WHERE entityType = :entityType AND entityId = :entityId")
    suspend fun getSyncLogByEntity(entityType: String, entityId: Long): SyncLog?

    @Query("SELECT * FROM sync_logs WHERE status = 'FAILED' ORDER BY lastAttemptAt ASC LIMIT :limit")
    suspend fun getFailedSyncLogsForRetry(limit: Int = 10): List<SyncLog>

    @Query("DELETE FROM sync_logs WHERE status = 'SUCCESS' AND createdAt < datetime('now', '-7 days')")
    suspend fun deleteOldSuccessfulSyncLogs()

    @Query("SELECT COUNT(*) FROM sync_logs WHERE status = :status")
    fun getSyncLogCount(status: SyncStatus): Flow<Int>
}
