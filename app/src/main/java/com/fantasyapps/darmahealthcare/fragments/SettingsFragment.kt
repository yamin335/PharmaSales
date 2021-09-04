package com.fantasyapps.darmahealthcare.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.LoginActivity

import com.fantasyapps.darmahealthcare.R




class SettingsFragment : BaseFragment() {
    internal var e_name: TextView? = null
    internal var e_email: TextView? = null
    internal var e_phone: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        //val actionBar = (activity as MainActivity).getSupportActionBar()
        //actionBar!!.hide()

        val btn_logout = view.findViewById<Button>(R.id.btn_logout)
        e_name = view.findViewById(R.id.employee_name)
        e_email = view.findViewById(R.id.employee_email)
        e_phone = view.findViewById(R.id.employee_phone)

        val preferences = context!!.getSharedPreferences("loggedIn", Context.MODE_PRIVATE)
        var uname = preferences.getString("emplyoee_name", "")
        e_name!!.setText(uname)



        btn_logout.setOnClickListener {
            val spe = preferences.edit()
            spe.clear()
            spe.apply()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }

        return view

    }


    companion object {
        fun newInstance(instance: Int): SettingsFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment

        }
    }
}
