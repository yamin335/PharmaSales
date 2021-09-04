package com.fantasyapps.darmahealthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.fragments.DialogHistory
import com.fantasyapps.darmahealthcare.models.PunchDetails.PunchDetailsModel
import java.util.ArrayList
import com.fantasyapps.darmahealthcare.MainActivity
import com.fantasyapps.darmahealthcare.models.inTimes
import com.fantasyapps.darmahealthcare.models.outTimes


class PunchDetailsAdapter(var mItemList: ArrayList<PunchDetailsModel>, private val context: Context) : RecyclerView.Adapter<PunchDetailsAdapter.PunchViewHolder>() {
    private var mlist = ArrayList<PunchDetailsModel>()
    var inTimesList = ArrayList<inTimes>()
    var outTimesList = ArrayList<outTimes>()

    init {
        this.mlist = mItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): PunchDetailsAdapter.PunchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_punchdetails, parent, false)
        return PunchViewHolder(v)
    }

    override fun onBindViewHolder(holder: PunchDetailsAdapter.PunchViewHolder, position: Int) {
        val model = mItemList[position]

        if (model.name != null) {
            holder.displayName.text = model.name
        }
        if (model.date != null) {
            holder.date.text = model.date
        }
        if (model.totalHourWorked != null) {
            holder.totalHour.text = model.totalHourWorked
        }
        if (model.inTime != null) {
            holder.inTime.text = model.inTime
        }
        if (model.outTime != null) {
            holder.outTime.text = model.outTime
        }

        if (model.overtime != null) {
            holder.overTime.text = model.overtime
        }

        if (model.isLate != null) {
            holder.isLate.text = model.isLate
        }

        if (model.lateTime != null) {
            holder.lateTime.text = model.lateTime
        }

        for (i in model.inTimes.indices) {
            inTimesList.add(inTimes(model.inTimes.get(i)))
        }

        for (i in model.outTimes.indices) {
            outTimesList.add(outTimes(model.outTimes.get(i)))
        }

        holder.btnHistory.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                    val fragmentManager = (context as MainActivity).supportFragmentManager
                    val requestDialogFragment = DialogHistory()
                    val d2Fragment = requestDialogFragment.getInstanceFor(inTimesList, outTimesList )
//                  requestDialogFragment.setDialogFragmentEvents(this@TaskFragment)
                    d2Fragment.show(fragmentManager, "request_dialog")
            }

        })






    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }


    inner class PunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var displayName: TextView
        internal var date: TextView
        internal var inTime: TextView
        internal var outTime: TextView
        internal var totalHour: TextView
        internal var overTime: TextView
        internal var lateTime: TextView
        internal var isLate: TextView
        internal var btnHistory: Button

        init {
            displayName = itemView.findViewById(R.id.tv_name)
            date = itemView.findViewById(R.id.tv_date)
            inTime = itemView.findViewById(R.id.tv_intime)
            outTime = itemView.findViewById(R.id.tv_outtime)
            totalHour = itemView.findViewById(R.id.tv_totalhour)
            overTime = itemView.findViewById(R.id.tv_overtime)
            lateTime = itemView.findViewById(R.id.tv_lateTime)
            isLate = itemView.findViewById(R.id.tv_islate)
            btnHistory = itemView.findViewById(R.id.history)

        }
    }
}
