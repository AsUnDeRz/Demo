package asunder.toche.demo.dao

import android.arch.persistence.room.*
import asunder.toche.demo.model.OpsOrder

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Dao
abstract class OpsOrderDao{

    @Query("SELECT * FROM OpsOrder")
    abstract fun getOpsOrder() : List<OpsOrder>

    @Insert
    abstract fun insert(order : List<OpsOrder>)

    @Insert
    abstract fun insert(order : OpsOrder)

    @Update
    abstract fun update(order: OpsOrder)

    @Delete
    abstract fun delete(order: OpsOrder)

}