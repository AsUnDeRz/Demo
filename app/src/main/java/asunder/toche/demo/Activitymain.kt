package asunder.toche.demo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import asunder.toche.demo.dao.MasCustomerDao
import asunder.toche.demo.model.MasCustomer
import asunder.toche.demo.view.ActivityCustomer
import asunder.toche.demo.view.ActivityOrder
import asunder.toche.demo.view.ActivityProduct
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ActivityMain : AppCompatActivity(),HasSupportFragmentInjector{


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    var TAG = "MAIN"


    @Inject
    lateinit var masCustomerDao : MasCustomerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        masCustomerDao.getMasCustomer().forEach {
            Log.d(TAG,it.CustomerCode)
        }

        btnCustomer.setOnClickListener {
            startActivity(Intent().setClass(this@ActivityMain,ActivityCustomer::class.java))
        }
        btnProduct.setOnClickListener {
            startActivity(Intent().setClass(this@ActivityMain,ActivityProduct::class.java))
        }
        btnOrder.setOnClickListener {
            startActivity(Intent().setClass(this@ActivityMain,ActivityOrder::class.java))
        }
    }




    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
