package com.fantasyapps.darmahealthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.models.inTimes
import java.util.ArrayList

class InTimeAdapter (var mItemList: ArrayList<inTimes>, private val context: Context) : RecyclerView.Adapter<InTimeAdapter.PunchViewHolder>() {
    private var mlist = ArrayList<inTimes>()

    init {
        this.mlist = mItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): InTimeAdapter.PunchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_intime, parent, false)
        return PunchViewHolder(v)
    }

    override fun onBindViewHolder(holder: InTimeAdapter.PunchViewHolder, position: Int) {
        val model = mItemList[position]
        holder.item_inTime.text = model.intimes



    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }


    inner class PunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var item_inTime: TextView


        init {
            item_inTime = itemView.findViewById(R.id.itemIntime)

        }
    }
}
