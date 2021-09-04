package com.fantasyapps.darmahealthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.fragments.OrderCreateFragment
import com.fantasyapps.darmahealthcare.models.custom.ProductDetails
import java.util.ArrayList

class ProductListAdapter(val itemList: ArrayList<ProductDetails>, private val context: Context, private val mAdapterCallback: AdapterCallback) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    //onItemClickListner onItemClickListner;
   // private val mItemList = ArrayList<ProductDetails>()
    internal var mItemList: ArrayList<ProductDetails>? = null


    init {
        this.mItemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ProductListAdapter.ProductListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ProductListViewHolder, position: Int) {
        //animate(holder);

        val model = mItemList!![position]
        holder.p_name.text = model.getpName()
        holder.p_quantity.text = model.getpQty()!!.toString()
        holder.p_amount.text = model.getpAmount()!!.toString()
        //holder.p_name.setText(model.);
        holder.delete.setOnClickListener {
            removeAt(position)
            if (OrderCreateFragment.P_list != null) {
                OrderCreateFragment.P_list!!.removeAt(position)

                var subtotal: Double? = 0.0
                for (i in OrderCreateFragment.P_list!!.indices) {
                    val amount = OrderCreateFragment.P_list!![i].getpAmount()
                    subtotal = subtotal!! + amount!!
                }

                mAdapterCallback.onMethodCallback(subtotal) // callback for subtotal
            }
        }

        //        holder.edit.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //
        //               // ProductDetails adddetails = new ProductDetails("XXX", "XXX", 0.0, 0.0, "0");
        //                //insert(position, model);
        //
        //            }
        //        });

        //        holder.itemView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                onItemClickListner.onClick(mItemList.get(position));
        //            }
        //        });

    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }

    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var p_name: TextView
        internal var p_quantity: TextView
        internal var p_amount: TextView
        internal var delete: Button
        internal var edit: Button? = null

        init {
            p_name = itemView.findViewById(R.id.product_name)
            p_quantity = itemView.findViewById(R.id.product_quantity)
            p_amount = itemView.findViewById(R.id.product_amount)
            delete = itemView.findViewById(R.id.btn_delete)
            //            edit = itemView.findViewById(R.id.btn_edit);
        }
    }

    fun insert(position: Int, details: ProductDetails) {
        mItemList!!.add(position, details)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, mItemList!!.size)
    }


    fun removeAt(position: Int) {
        mItemList!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mItemList!!.size)
    }

    private fun refresh() {
        //        notifyItemInserted(mItemList.size());
        //        smoothScrollToPosition(mItemList.size());

    }

    //    public void setOnItemClickListner(ProductListAdapter.onItemClickListner onItemClickListner) {
    //        this.onItemClickListner = onItemClickListner;
    //    }

    //    public interface onItemClickListner {
    //        void onClick(ProductDetails details);//pass your object types.
    //    }

    fun animate(viewHolder: RecyclerView.ViewHolder) {
        val animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator)
        viewHolder.itemView.animation = animAnticipateOvershoot
    }

    interface AdapterCallback {
        fun onMethodCallback(subTotal: Double?)
    }
}
