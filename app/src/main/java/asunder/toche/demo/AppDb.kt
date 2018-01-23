package asunder.toche.demo

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import asunder.toche.demo.dao.MasCustomerDao
import asunder.toche.demo.dao.MasProductsDao
import asunder.toche.demo.dao.OpsOrderDao
import asunder.toche.demo.dao.OpsOrderDetailDao
import asunder.toche.demo.model.MasCustomer
import asunder.toche.demo.model.MasProducts
import asunder.toche.demo.model.OpsOrder
import asunder.toche.demo.model.OpsOrderDetail

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Database(entities = arrayOf(MasCustomer::class,MasProducts::class,OpsOrder::class,OpsOrderDetail::class),version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun masCustomerDao() : MasCustomerDao
    abstract fun masProductDao() : MasProductsDao
    abstract fun opsOrderDao() : OpsOrderDao
    abstract fun opsOrderDetailDao() : OpsOrderDetailDao

}