package asunder.toche.demo.dao

import android.arch.persistence.room.*
import asunder.toche.demo.model.MasCustomer

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Dao
abstract class MasCustomerDao{

    @Query("SELECT * FROM MasCustomer")
    abstract fun getMasCustomer() : List<MasCustomer>

    @Insert
    abstract fun insert(customer : List<MasCustomer>)

    @Insert
    abstract fun insert(customer : MasCustomer)

    @Update
    abstract fun update(customer: MasCustomer)

    @Delete
    abstract fun delete(customer: MasCustomer)

}