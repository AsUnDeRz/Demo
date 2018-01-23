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
import asunder.toche.demo.model.MasProducts
import com.arlib.floatingsearchview.util.Util

/**
 *Created by ToCHe on 23/1/2018 AD.
 */
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var mDataSet: ArrayList<MasProducts> = arrayListOf()


    private var mLastAnimatedItemPosition = -1

    private var mItemsOnClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(product: MasProducts)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mName: TextView
        val mActive: View

        init {
            mName = view.findViewById(R.id.txtName)
            mActive = view.findViewById(R.id.vActive)
        }
    }

    fun swapData(mNewDataSet: ArrayList<MasProducts>) {
        mDataSet = mNewDataSet
        notifyDataSetChanged()
    }

    fun setItemsOnClickListener(onClickListener: OnItemClickListener) {
        this.mItemsOnClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val data = mDataSet[pos]

        holder.mName.text = data.ProductName
        if(data.IsActive == 0){
            holder.mActive.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_red)
        }else{
            holder.mActive.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_green)
        }

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