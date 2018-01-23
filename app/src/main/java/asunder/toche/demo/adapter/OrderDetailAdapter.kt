package asunder.toche.demo.adapter

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import asunder.toche.demo.R
import asunder.toche.demo.model.OpsOrderDetail
import com.arlib.floatingsearchview.util.Util

/**
 *Created by ToCHe on 24/1/2018 AD.
 */
class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    var mDataSet: ArrayList<OpsOrderDetail> = arrayListOf()


    private var mLastAnimatedItemPosition = -1

    private var mItemsOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(customer: OpsOrderDetail)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mName: TextView
        val mQty: TextView

        init {
            mName = view.findViewById(R.id.txtProductCode)
            mQty = view.findViewById(R.id.txtQTY)
        }
    }

    fun addDetail(detail: OpsOrderDetail){
        mDataSet.add(detail)
        notifyDataSetChanged()
    }
    fun swapData(mNewDataSet: ArrayList<OpsOrderDetail>) {
        mDataSet = mNewDataSet
        notifyDataSetChanged()
    }

    fun setItemsOnClickListener(onClickListener: OnItemClickListener) {
        this.mItemsOnClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val data = mDataSet[pos]

        holder.mName.text  = data.ProductCode
        holder.mQty.text = data.QTY.toString()

        if (mLastAnimatedItemPosition < position) {
            animateItem(holder.itemView)
            mLastAnimatedItemPosition = position
        }

        if (mItemsOnClickListener != null) {
            holder.itemView.setOnClickListener {
                mItemsOnClickListener!!.onClick(mDataSet[pos]) }
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    private fun animateItem(view: View) {
        view.translationY = Util.getScreenHeight(view.context as Activity).toFloat()
        view.animate()
                .translationY(0f)
                .setInterpolator(DecelerateInterpolator(3f))
                .setDuration(700)
                .start()
    }
}