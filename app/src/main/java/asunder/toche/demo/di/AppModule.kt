package asunder.toche.demo.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import asunder.toche.demo.AppDb
import asunder.toche.demo.dao.MasCustomerDao
import asunder.toche.demo.dao.MasProductsDao
import asunder.toche.demo.dao.OpsOrderDao
import asunder.toche.demo.dao.OpsOrderDetailDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Module
internal  class AppModule{

    @Singleton
    @Provides
    fun providePrefer(app: Application) : SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(app.baseContext)
    }

    @Singleton
    @Provides
    fun provideDb(app : Application) : AppDb{
        return Room.databaseBuilder(app,AppDb::class.java,"demo.db").allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideMasCustomerDao(db: AppDb) : MasCustomerDao {
        return db.masCustomerDao()
    }

    @Singleton
    @Provides
    fun provideMasProductsDao(db: AppDb) : MasProductsDao{
        return db.masProductDao()
    }

    @Singleton
    @Provides
    fun provideOpsOrderDao(db: AppDb) : OpsOrderDao{
        return db.opsOrderDao()
    }

    @Singleton
    @Provides
    fun provideOpsOrderDetailDao(db: AppDb) : OpsOrderDetailDao{
        return db.opsOrderDetailDao()
    }


}