package asunder.toche.demo.view

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import asunder.toche.demo.R
import asunder.toche.demo.adapter.CustomerAdapter
import asunder.toche.demo.dao.MasCustomerDao
import asunder.toche.demo.model.MasCustomer
import asunder.toche.demo.utils.DataHelper
import asunder.toche.demo.utils.PreferHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_content_list.*
import kotlinx.android.synthetic.main.sheet_customer.*
import javax.inject.Inject


/**
 *Created by ToCHe on 22/1/2018 AD.
 */
class ActivityCustomer  : AppCompatActivity(),CustomerAdapter.OnItemClickListener,HasSupportFragmentInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var masCustomerDao : MasCustomerDao
    @Inject
    lateinit var preferences: SharedPreferences

    lateinit var customerAdapter : CustomerAdapter
    lateinit var sheetBehavior:BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)
        sheetBehavior = BottomSheetBehavior.from(sheet_customer)
        titleName.text = "Name"
        initAction()
        initData()
    }

    fun initData(){
        val customers = loadCustomer()
        customerAdapter = CustomerAdapter()
        customerAdapter.swapData(ArrayList(customers))
        customerAdapter.setItemsOnClickListener(this)
        rvCustomer.adapter = customerAdapter
        rvCustomer.layoutManager = LinearLayoutManager(this)
    }

    fun initAction(){
        btnAddCustomer.setOnClickListener {
            insertCustomer()
        }

        btnUpdateCustomer.setOnClickListener {
            updateCustomer()
        }

        btnDeleteCustomer.setOnClickListener {
            deleteCustomer()
        }

        floating_search_view.setOnHomeActionClickListener {
            onBackPressed()
        }

        floating_search_view.setSearchHint("Search Customer...")
        floating_search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            Log.d("Customer","oldQ [$oldQuery] newQ [$newQuery]")
            DataHelper.findCustomer(newQuery,object : DataHelper.OnFindCustomerListener{
                override fun onResults(results: ArrayList<MasCustomer>) {
                    customerAdapter.swapData(results)
                }
            }, ArrayList(loadCustomer()))
        }

        floatingActionButton.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
            btnAddCustomer.visibility = View.VISIBLE
            btnDeleteCustomer.visibility = View.GONE
            btnUpdateCustomer.visibility = View.GONE
            clearSheet()
        }


    }

    fun setModelToSheet(model : MasCustomer){
        btnAddCustomer.visibility = View.GONE
        btnDeleteCustomer.visibility = View.VISIBLE
        btnUpdateCustomer.visibility = View.VISIBLE

        edtCustomerCode.text = model.CustomerCode
        edtFname.setText(model.FistName)
        edtLname.setText(model.LastName)
        edtIDcard.setText(model.IDCard)
        edtTel.setText(model.Tel)

        if(model.IsActive != 0){
            customerActiveGroup.check(customerActive.id)
        }else{
            customerActiveGroup.check(customerInActive.id)
        }
    }

    fun loadCustomer() : List<MasCustomer>{
        return masCustomerDao.getMasCustomer()
    }

    fun reloadData(){
        customerAdapter.swapData(ArrayList(loadCustomer()))
        hideBottomSheet()
    }

    fun insertCustomer(){
        if(validate()){
            val data = MasCustomer(edtCustomerCode.text.toString()
                    ,edtFname.text.toString()
                    ,edtLname.text.toString()
                    ,edtIDcard.text.toString()
                    ,edtTel.text.toString()
                    ,if (customerActiveGroup.checkedRadioButtonId == customerActive.id ) 1 else 0)
            masCustomerDao.insert(data)
            PreferHelper.increseID(preferences,PreferHelper.KEYCUSID)
            reloadData()
        }

    }

    fun updateCustomer(){
        if(validate()){
            val data = MasCustomer(edtCustomerCode.text.toString()
                    ,edtFname.text.toString()
                    ,edtLname.text.toString()
                    ,edtIDcard.text.toString()
                    ,edtTel.text.toString()
                    ,if (customerActiveGroup.checkedRadioButtonId == customerActive.id ) 1 else 0)
            masCustomerDao.update(data)
            reloadData()
        }
    }

    fun deleteCustomer(){
        if(validate()){
            val data = MasCustomer(edtCustomerCode.text.toString()
                    ,edtFname.text.toString()
                    ,edtLname.text.toString()
                    ,edtIDcard.text.toString()
                    ,edtTel.text.toString()
                    ,if (customerActiveGroup.checkedRadioButtonId == customerActive.id ) 1 else 0)
            masCustomerDao.delete(data)
            reloadData()
        }
    }

    fun validate() : Boolean{
        if(TextUtils.isEmpty(edtCustomerCode.text)){
            return false
        }
        if(TextUtils.isEmpty(edtFname.text)){
            return false
        }
        if(TextUtils.isEmpty(edtLname.text)){
            return false
        }
        if(TextUtils.isEmpty(edtIDcard.text)){
            return false
        }
        if(TextUtils.isEmpty(edtTel.text)){
            return false
        }
        return true
    }

    fun hideBottomSheet(){
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun clearSheet(){
        edtTel.setText("")
        edtLname.setText("")
        edtFname.setText("")
        edtIDcard.setText("")
        edtCustomerCode.text = DataHelper.genCustomerID(preferences)
    }

    override fun onClick(customer: MasCustomer) {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        setModelToSheet(customer)
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


}