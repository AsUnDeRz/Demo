package asunder.toche.demo.di

import android.support.v4.app.Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): Fragment
}