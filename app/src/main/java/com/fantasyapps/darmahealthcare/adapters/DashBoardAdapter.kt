package com.fantasyapps.darmahealthcare.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.models.SalesModule.SalesData
import java.lang.NullPointerException
import java.util.ArrayList

class DashBoardAdapter(private var context: Context?, mItemList: MutableList<SalesData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemList: MutableList<SalesData>? = ArrayList()
    private val mDashBoardAdapterCallback: DashBoardAdapterCallback? = null
    private val ANIMATION_DURATION = 1000

    private val expandState = SparseBooleanArray()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private var isLoadingAdded = false

    var itemList: MutableList<SalesData>?
        get() = mItemList
        set(mItemList) {
            this.mItemList = mItemList
        }

    val isEmpty: Boolean
        get() = itemCount == 0

    init {
        this.mItemList = mItemList
        //set initial expanded state to false
        for (i in mItemList.indices) {
            expandState.append(i, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context

        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
            return ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
            return LoadingViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            populateItemRows(holder, position)
        } else if (holder is LoadingViewHolder) {
            showLoadingView(holder, position)
        }
    }

    private fun populateItemRows(holder: ItemViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        //animate(holder);

        val model = mItemList!![position]
        try {

            Handler(Looper.getMainLooper()).post(Runnable {

                if (model.customer.name != null){
                    holder.customer.text = model.customer.name
                }

                if (model.date != null){
                    holder.date.text = model.date
                }

                if (model.cartTotal != null){
                    holder.amount.text = model.cartTotal.toString()
                }

                if (model.ourReference != null){
                    holder.invoice_id.text = model.ourReference
                }


                if (model.cartTotal != null){
                    holder.amount_2.text = model.cartTotal.toString()
                }

                if (model.grandTotal != null){
                    holder.grandTotal.text = model.grandTotal.toString()
                }

                if (model.vat != null){
                    holder.vat.text = model.vat.toString()
                }

                if (model.branch != null){
                    holder.branch.text = model.branch.name
                }

                if (model.discount != null) {
                    holder.discount.text = model.discount.toString()
                }

                val stat = model.status
                holder.status.text = stat
                when (stat) {
                    "Pending" -> holder.status.setTextColor(Color.parseColor("#FF8C00"))
                    "Paid" -> holder.status.setTextColor(Color.parseColor("#008000"))
                    "Rejected" -> holder.status.setTextColor(Color.parseColor("#880000"))
                    "Cancelled" -> holder.status.setTextColor(Color.parseColor("#7D2918"))
                    "Accepted" -> holder.status.setTextColor(Color.parseColor("#04447c"))
                }


            })

        } catch (e: NullPointerException) {
            error(this@DashBoardAdapter , e.localizedMessage)
        }




        val isExpanded = expandState.get(position)
        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.showButton.rotation = if (expandState.get(position)) 180f else 0f
        //        holder.showButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(final View v) {
        //                onClickButton(holder.expandableLayout, holder.showButton,  position);
        //            }
        //        });

        holder.showLayout.setOnClickListener { onClickButton(holder.expandableLayout, holder.showButton, position) }

    }


    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //Progressbar displayed here

    }

    private inner class LoadingViewHolder(progressView: View) : RecyclerView.ViewHolder(progressView) {
        internal var progressBar: ProgressBar

        init {
            progressBar = progressView.findViewById(R.id.progressBar1)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        //return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        return if (position == mItemList!!.size - 1 && isLoadingAdded) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customer: TextView
        val date: TextView
        val invoice_id: TextView
        val amount: TextView
        val vat: TextView
        val discount: TextView
        val grandTotal: TextView
        val branch: TextView
        val status: TextView
        val amount_2: TextView
        val showButton: RelativeLayout
        val expandableLayout: RelativeLayout
        val showLayout: RelativeLayout

        init {

            customer = itemView.findViewById(R.id.tv_customer)
            date = itemView.findViewById(R.id.tv_date)
            invoice_id = itemView.findViewById(R.id.tv_invoiceID)
            amount = itemView.findViewById(R.id.tv_amount)
            vat = itemView.findViewById(R.id.tv_vat)
            discount = itemView.findViewById(R.id.tv_discount)
            grandTotal = itemView.findViewById(R.id.tv_total)
            branch = itemView.findViewById(R.id.tv_branch)
            status = itemView.findViewById(R.id.tv_status)
            amount_2 = itemView.findViewById(R.id.tv_amount2)

            showButton = itemView.findViewById(R.id.toggle_button)
            showLayout = itemView.findViewById(R.id.hints)
            //hide = itemView.findViewById(R.id.iv_hide);
            expandableLayout = itemView.findViewById(R.id.rl_details)
        }
    }


    fun add(r: SalesData) {
        mItemList!!.add(r)
        notifyItemInserted(mItemList!!.size - 1)
    }

    fun addAll(modelList: List<SalesData>) {
        for (result in modelList) {
            add(result)
        }
    }

    fun remove(r: SalesData?) {
        val position = mItemList!!.indexOf(r)
        if (position > -1) {
            mItemList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(SalesData())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        try {
            val position = mItemList!!.size - 1
            val result = getItem(position)

            if (result != null) {
                mItemList!!.removeAt(position)
                notifyItemRemoved(position)
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }

    }

    fun getItem(position: Int): SalesData? {
        return mItemList!![position]
    }

    fun animate(viewHolder: RecyclerView.ViewHolder) {
        val animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
        viewHolder.itemView.animation = animAnticipateOvershoot
    }

    interface DashBoardAdapterCallback {
        fun onMethodCallback(view: View)
    }

    private fun onClickButton(expandableLayout: RelativeLayout, buttonLayout: RelativeLayout, i: Int) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.visibility == View.VISIBLE) {
            createRotateAnimator(buttonLayout, 180f, 0f).start()
            expandableLayout.visibility = View.GONE
            expandState.put(i, false)
        } else {
            createRotateAnimator(buttonLayout, 0f, 180f).start()
            expandableLayout.visibility = View.VISIBLE
            expandState.put(i, true)
        }
    }


    //Code to rotate button
    private fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = LinearInterpolator()
        return animator
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

}
