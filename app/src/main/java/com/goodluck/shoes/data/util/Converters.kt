package com.goodluck.shoes.data.util

import androidx.room.TypeConverter
import com.goodluck.shoes.data.models.OrderStatus
import com.goodluck.shoes.data.models.PaymentMethod
import com.goodluck.shoes.data.models.PaymentStatus
import com.goodluck.shoes.data.models.ShoeSize
import com.goodluck.shoes.data.models.ShoeType
import com.goodluck.shoes.data.models.SyncAction
import com.goodluck.shoes.data.models.SyncStatus
import com.goodluck.shoes.data.models.UserRole
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromUserRole(role: UserRole): String = role.name

    @TypeConverter
    fun toUserRole(role: String): UserRole = UserRole.valueOf(role)

    @TypeConverter
    fun fromShoeType(type: ShoeType): String = type.name

    @TypeConverter
    fun toShoeType(type: String): ShoeType = ShoeType.valueOf(type)

    @TypeConverter
    fun fromShoeSize(size: ShoeSize): String = size.name

    @TypeConverter
    fun toShoeSize(size: String): ShoeSize = ShoeSize.valueOf(size)

    @TypeConverter
    fun fromOrderStatus(status: OrderStatus): String = status.name

    @TypeConverter
    fun toOrderStatus(status: String): OrderStatus = OrderStatus.valueOf(status)

    @TypeConverter
    fun fromPaymentStatus(status: PaymentStatus): String = status.name

    @TypeConverter
    fun toPaymentStatus(status: String): PaymentStatus = PaymentStatus.valueOf(status)

    @TypeConverter
    fun fromPaymentMethod(method: PaymentMethod?): String? = method?.name

    @TypeConverter
    fun toPaymentMethod(method: String?): PaymentMethod? = method?.let { PaymentMethod.valueOf(it) }

    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String = status.name

    @TypeConverter
    fun toSyncStatus(status: String): SyncStatus = SyncStatus.valueOf(status)

    @TypeConverter
    fun fromSyncAction(action: SyncAction): String = action.name

    @TypeConverter
    fun toSyncAction(action: String): SyncAction = SyncAction.valueOf(action)
}
