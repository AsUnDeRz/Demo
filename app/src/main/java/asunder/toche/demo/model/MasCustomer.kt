package asunder.toche.demo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Entity
data class MasCustomer(@PrimaryKey val CustomerCode:String,
                       val FistName:String,
                       val LastName:String,
                       val IDCard:String,
                       val Tel:String,
                       val IsActive:Int)