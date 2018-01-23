package asunder.toche.demo.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import asunder.toche.demo.R
import asunder.toche.demo.adapter.OrderAdapter
import asunder.toche.demo.adapter.OrderDetailAdapter
import asunder.toche.demo.dao.MasCustomerDao
import asunder.toche.demo.dao.MasProductsDao
import asunder.toche.demo.dao.OpsOrderDao
import asunder.toche.demo.dao.OpsOrderDetailDao
import asunder.toche.demo.model.OpsOrder
import asunder.toche.demo.model.OpsOrderDetail
import asunder.toche.demo.utils.DataHelper
import asunder.toche.demo.utils.PreferHelper
import com.afollestad.materialdialogs.MaterialDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.sheet_order_detail.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
class ActivityOrder : AppCompatActivity(),
        OrderDetailAdapter.OnItemClickListener,
        OrderAdapter.OnItemClickListener,HasSupportFragmentInjector{

    val titleCustomer :ArrayList<String> = arrayListOf()
    val titleProduct : ArrayList<String> = arrayListOf()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var masCustomerDao : MasCustomerDao
    @Inject
    lateinit var masProductDao: MasProductsDao
    @Inject
    lateinit var opsOrderDetailDao: OpsOrderDetailDao
    @Inject
    lateinit var opsOrderDao: OpsOrderDao
    @Inject
    lateinit var preferences: SharedPreferences

    lateinit var sheetBehavior: BottomSheetBehavior<*>
    lateinit var orderAdapter: OrderAdapter
    lateinit var orderDetailAdapter: OrderDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        sheetBehavior = BottomSheetBehavior.from(sheet_order_detail)
        initAction()
        initOrder()
        initOrderDetail()
    }

    fun initAction(){
        edtCusName.setOnClickListener { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            showCustomer()
        }
        edtCusName.isFocusableInTouchMode = false
        edtCusName.isFocusable = false

        edtProCode.setOnClickListener { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            showProduct()
        }
        edtProCode.isFocusableInTouchMode = false
        edtProCode.isFocusable = false

        btnCreateOrder.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            txtOrderCode.text = DataHelper.genOrderID(preferences)
            edtDate.setText(DataHelper.getDateString(Date()))
            edtProCode.setText("")
            edtQTY.setText("")
            edtCusName.setText("")
            initOrderDetail()
        }

        btnAddDetail.setOnClickListener {
            addOrderDetail()
        }

        btnAddOrder.setOnClickListener {
            if(validate()){
                val order = OpsOrder(txtOrderCode.text.toString(),
                        edtCusName.text.toString(),Date().time,
                        if (orderActiveGroup.checkedRadioButtonId == orderActive.id ) 1 else 0)
                opsOrderDao.insert(order)
                PreferHelper.increseID(preferences,PreferHelper.KEYORDERID)
                opsOrderDetailDao.insert(orderDetailAdapter.mDataSet)
                reloadData()

                opsOrderDao.getOpsOrder().forEach {
                    Log.d("OPS",it.toString())
                }
                opsOrderDetailDao.getOpsOrderDetail().forEach {
                    Log.d("OPS Detail",it.toString())
                }
            }

        }
    }

    fun reloadData(){
        orderAdapter.swapData(ArrayList(opsOrderDao.getOpsOrder()))
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun initOrder(){
        orderAdapter = OrderAdapter()
        orderAdapter.swapData(ArrayList(opsOrderDao.getOpsOrder()))
        orderAdapter.setItemsOnClickListener(this)
        rvOrder.adapter = orderAdapter
        rvOrder.layoutManager = LinearLayoutManager(this)

        masCustomerDao.getMasCustomer().forEach {
            titleCustomer.add(it.FistName+" "+it.LastName)
        }
        masProductDao.getMasProducts().forEach {
            titleProduct.add(it.ProductName)
        }

    }

    fun initOrderDetail(){
        orderDetailAdapter = OrderDetailAdapter()
        orderDetailAdapter.setItemsOnClickListener(this)
        rvOrderDetail.adapter = orderDetailAdapter
        rvOrderDetail.layoutManager = LinearLayoutManager(this)



    }

    fun addOrderDetail(){
        val detail = OpsOrderDetail(0,
                txtOrderCode.text.toString(),
                edtProCode.text.toString(),
                if (TextUtils.isEmpty(edtQTY.text)) 1 else edtQTY.text.toString().toInt())
        orderDetailAdapter.addDetail(detail)
    }

    fun mockDetail() : List<OpsOrderDetail>{
        val result : ArrayList<OpsOrderDetail> = arrayListOf()
        for (i in 1..20){
            result.add(OpsOrderDetail(i,"O-YYYYMM1231","PYYYYMM1231",i))
        }
        return result
    }
    fun mockOrder() : List<OpsOrder>{
        val result : ArrayList<OpsOrder> = arrayListOf()
        for (i in 1..20){
            result.add(OpsOrder("O-YYYYMM123$i","CYYYYMM02$i",Date().time,1))
        }
        return result
    }

    fun showCustomer(){
        MaterialDialog.Builder(this)
                .title("Customer")
                .items(titleCustomer)
                .itemsCallback({ dialog, view, which, text ->
                    val result = masCustomerDao.getMasCustomer()[which]
                    edtCusName.setText(result.CustomerCode)

                })
                .positiveText(android.R.string.cancel)
                .show()
    }

    fun showProduct(){
        MaterialDialog.Builder(this)
                .title("Product")
                .items(titleProduct)
                .itemsCallback({ dialog, view, which, text ->
                    val result = masProductDao.getMasProducts()[which]
                    edtProCode.setText(result.ProductCode)

                })
                .positiveText(android.R.string.cancel)
                .show()
    }

    fun validate() : Boolean{
        if(TextUtils.isEmpty(edtProCode.text)){
            return false
        }
        if(TextUtils.isEmpty(edtCusName.text)){
            return false
        }
        return true
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onClick(customer: OpsOrder) {
    }

    override fun onClick(customer: OpsOrderDetail) {
    }

}