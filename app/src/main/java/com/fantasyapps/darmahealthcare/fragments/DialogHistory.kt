package com.fantasyapps.darmahealthcare.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.adapters.InTimeAdapter
import com.fantasyapps.darmahealthcare.adapters.OutTimeAdapter
import com.fantasyapps.darmahealthcare.models.inTimes
import com.fantasyapps.darmahealthcare.models.outTimes
import java.util.ArrayList



class DialogHistory  : DialogFragment(){
    private var list = ArrayList<inTimes>()
    private var outlist = ArrayList<outTimes>()

    private var btnSend: TextView? = null
    private var btnDismiss: TextView? = null

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var recyclerOutView: RecyclerView
    internal var inTimeAdapter: InTimeAdapter? = null
    internal var outTimeAdapter: OutTimeAdapter? = null

    fun getInstanceFor(list: ArrayList<inTimes>, outlist: ArrayList<outTimes>): DialogHistory {
        val d2Fragment = DialogHistory()
        d2Fragment.list = list
        d2Fragment.outlist = outlist
        return d2Fragment
    }

//    internal lateinit var mCallback: DataPassListener


//    fun setDialogFragmentEvents(mCallback: DataPassListener) {
//        this.mCallback = mCallback
//    }
//
//
//    interface DataPassListener {
//        fun passData(isSuccess: Boolean)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_history, container, false)
        btnDismiss = rootView.findViewById(R.id.dismiss)
        btnDismiss!!.setOnClickListener { dismiss() }

        recyclerView = rootView!!.findViewById(R.id.intimeRecycler)
        recyclerOutView = rootView!!.findViewById(R.id.outtimeRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerOutView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemAnim = DefaultItemAnimator()
        itemAnim.addDuration = 1000
        itemAnim.removeDuration = 1000
        recyclerView.itemAnimator = itemAnim
        recyclerOutView.itemAnimator = itemAnim

//        Log.e("===============InTimes", list.size.toString())

        inTimeAdapter = InTimeAdapter(list, context!!)
        outTimeAdapter = OutTimeAdapter(outlist, context!!)
        recyclerView.adapter = inTimeAdapter
        recyclerOutView.adapter = outTimeAdapter
        inTimeAdapter!!.notifyDataSetChanged()
        outTimeAdapter!!.notifyDataSetChanged()

        return rootView
    }




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // request a window without the title
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.history_rounded)
        return dialog
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onResume() {
        val window = dialog.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }


}
