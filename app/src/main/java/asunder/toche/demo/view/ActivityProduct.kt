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
import asunder.toche.demo.adapter.ProductAdapter
import asunder.toche.demo.dao.MasProductsDao
import asunder.toche.demo.model.MasProducts
import asunder.toche.demo.utils.DataHelper
import asunder.toche.demo.utils.PreferHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_content_list.*
import kotlinx.android.synthetic.main.sheet_product.*
import javax.inject.Inject

/**
 *Created by ToCHe on 22/1/2018 AD.
 */
class ActivityProduct : AppCompatActivity() , ProductAdapter.OnItemClickListener,HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var masProductDao : MasProductsDao
    @Inject
    lateinit var preferences: SharedPreferences

    lateinit var productAdapter: ProductAdapter
    lateinit var sheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)
        sheetBehavior = BottomSheetBehavior.from(sheet_product)
        titleName.text = "Product name"
        initData()
        initAction()
    }

    fun initData() {
        val data = loadProduct()
        productAdapter = ProductAdapter()
        productAdapter.swapData(ArrayList(data))
        productAdapter.setItemsOnClickListener(this)
        rvCustomer.adapter = productAdapter
        rvCustomer.layoutManager = LinearLayoutManager(this)
    }

    fun initAction(){
        btnAddProduct.setOnClickListener {
            insertProduct()
        }

        btnUpdateProduct.setOnClickListener {
            updateProduct()
        }

        btnDeleteProduct.setOnClickListener {
            deleteProduct()
        }

        floatingActionButton.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
            btnAddProduct.visibility = View.VISIBLE
            btnDeleteProduct.visibility = View.GONE
            btnUpdateProduct.visibility = View.GONE
            clearSheet()
        }
        floating_search_view.setSearchHint("Search Product...")
        floating_search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            Log.d("Product", "oldQ [$oldQuery] newQ [$newQuery]")
            DataHelper.findProduct(newQuery, object : DataHelper.OnFindProductListener {
                override fun onResults(results: ArrayList<MasProducts>) {
                    productAdapter.swapData(results)
                }
            }, ArrayList(loadProduct()))
        }

        floating_search_view.setOnHomeActionClickListener {
            onBackPressed()
        }

    }

    fun setModelToSheet(model : MasProducts){
        btnAddProduct.visibility = View.GONE
        btnDeleteProduct.visibility = View.VISIBLE
        btnUpdateProduct.visibility = View.VISIBLE

        edtProductCode.text = model.ProductCode
        edtPname.setText(model.ProductName)
        edtPdetail.setText(model.ProductDescription)


        if(model.IsActive != 0){
            productActiveGroup.check(productActive.id)
        }else{
            productActiveGroup.check(productInActive.id)
        }
    }

    fun loadProduct() : List<MasProducts>{
        return masProductDao.getMasProducts()
    }

    fun reloadData(){
        productAdapter.swapData(ArrayList(loadProduct()))
        hideBottomSheet()
    }

    fun insertProduct(){
        if(validate()){
            val data = MasProducts(edtProductCode.text.toString()
                    ,edtPname.text.toString()
                    ,edtPdetail.text.toString(),
                    if (productActiveGroup.checkedRadioButtonId == productActive.id ) 1 else 0)
            masProductDao.insert(data)
            PreferHelper.increseID(preferences,PreferHelper.KEYPROID)
            reloadData()
        }

    }

    fun updateProduct(){
        if(validate()){
            val data = MasProducts(edtProductCode.text.toString()
                    ,edtPname.text.toString()
                    ,edtPdetail.text.toString(),
                    if (productActiveGroup.checkedRadioButtonId == productActive.id ) 1 else 0)
            masProductDao.update(data)
            reloadData()
        }
    }

    fun deleteProduct(){
        if(validate()){
            val data = MasProducts(edtProductCode.text.toString()
                    ,edtPname.text.toString()
                    ,edtPdetail.text.toString(),
                    if (productActiveGroup.checkedRadioButtonId == productActive.id ) 1 else 0)
            masProductDao.delete(data)
            reloadData()
        }
    }

    fun validate() : Boolean{
        if(TextUtils.isEmpty(edtProductCode.text)){
            return false
        }
        if(TextUtils.isEmpty(edtPname.text)){
            return false
        }
        if(TextUtils.isEmpty(edtPdetail.text)){
            return false
        }
        return true
    }

    fun hideBottomSheet(){
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun clearSheet(){
        edtPdetail.setText("")
        edtPname.setText("")
        edtProductCode.text = DataHelper.genProductID(preferences)
    }

    override fun onClick(product: MasProducts) {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        setModelToSheet(product)
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


}