package asunder.toche.demo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Entity
data class MasProducts(@PrimaryKey val ProductCode:String,
                       val ProductName:String,
                       val ProductDescription:String,
                       val IsActive:Int)