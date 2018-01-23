package asunder.toche.demo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = OpsOrder::class,
        parentColumns = arrayOf("OrderCode"),
        childColumns = arrayOf("OrderCode"),
        onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = MasProducts::class,
                parentColumns = arrayOf("ProductCode"),
                childColumns = arrayOf("ProductCode"),
                onDelete = ForeignKey.CASCADE)))
data class OpsOrderDetail(@PrimaryKey(autoGenerate = true) val OrderDetailID:Int,
                    val OrderCode:String,
                    val ProductCode: String,
                    val QTY:Int)