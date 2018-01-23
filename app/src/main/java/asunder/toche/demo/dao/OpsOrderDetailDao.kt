package asunder.toche.demo.dao

import android.arch.persistence.room.*
import asunder.toche.demo.model.OpsOrderDetail

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Dao
abstract class OpsOrderDetailDao{

    @Query("SELECT * FROM OpsOrderDetail")
    abstract fun getOpsOrderDetail() : List<OpsOrderDetail>

    @Insert
    abstract fun insert(orderDetail : List<OpsOrderDetail>)

    @Insert
    abstract fun insert(orderDetail : OpsOrderDetail)

    @Update
    abstract fun update(orderDetail: OpsOrderDetail)

    @Delete
    abstract fun delete(orderDetail: OpsOrderDetail)

}