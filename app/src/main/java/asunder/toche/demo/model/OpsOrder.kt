package asunder.toche.demo.model

import android.arch.persistence.room.*
import java.util.*

/**
 *Created by ToCHe on 22/1/2018 AD.
 */

@Entity(foreignKeys = [(ForeignKey(entity = MasCustomer::class,
        parentColumns = arrayOf("CustomerCode"),
        childColumns = arrayOf("CustomerCode"),
        onDelete = ForeignKey.CASCADE))])
data class OpsOrder(@PrimaryKey val OrderCode:String,
                    val CustomerCode:String,
                    val OrderDate:Long,
                    val IsActive:Int)