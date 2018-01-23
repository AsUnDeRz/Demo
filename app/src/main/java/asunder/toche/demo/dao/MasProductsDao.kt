package asunder.toche.demo.dao

import android.arch.persistence.room.*
import asunder.toche.demo.model.MasProducts

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Dao
abstract class MasProductsDao{

    @Query("SELECT * FROM MasProducts")
    abstract fun getMasProducts() : List<MasProducts>

    @Insert
    abstract fun insert(product : List<MasProducts>)

    @Insert
    abstract fun insert(product : MasProducts)

    @Update
    abstract fun update(product : MasProducts)

    @Delete
    abstract fun delete(product : MasProducts)

}