package com.goodluck.shoes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

enum class SyncStatus {
    PENDING,
    IN_PROGRESS,
    SUCCESS,
    FAILED
}

enum class SyncAction {
    CREATE,
    UPDATE,
    DELETE
}

// Tracks sync operations for offline-first architecture
@Entity(tableName = "sync_logs")
data class SyncLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val entityType: String, // e.g., "Order", "Review", "Inventory"
    val entityId: Long,
    val action: SyncAction,
    val status: SyncStatus = SyncStatus.PENDING,
    val payload: String, // JSON data to sync
    val errorMessage: String? = null,
    val retryCount: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastAttemptAt: LocalDateTime? = null
)
