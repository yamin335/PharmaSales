package com.fantasyapps.darmahealthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.models.outTimes
import java.util.ArrayList

class OutTimeAdapter (var mItemList: ArrayList<outTimes>, private val context: Context) : RecyclerView.Adapter<OutTimeAdapter.PunchViewHolder>() {
    private var mlist = ArrayList<outTimes>()

    init {
        this.mlist = mItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): OutTimeAdapter.PunchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_outtime, parent, false)
        return PunchViewHolder(v)
    }

    override fun onBindViewHolder(holder: OutTimeAdapter.PunchViewHolder, position: Int) {
        val model = mItemList[position]
        holder.item_outTime.text = model.outtimes



    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }


    inner class PunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var item_outTime: TextView


        init {
            item_outTime = itemView.findViewById(R.id.itemOuttime)

        }
    }
}
