package asunder.toche.demo.di

import asunder.toche.demo.ActivityMain
import asunder.toche.demo.view.ActivityCustomer
import asunder.toche.demo.view.ActivityOrder
import asunder.toche.demo.view.ActivityProduct
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeActivityMain(): ActivityMain

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeActivityCustomer() : ActivityCustomer

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeActivityProduct() : ActivityProduct

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeActivityOrder() : ActivityOrder
}