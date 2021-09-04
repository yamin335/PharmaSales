package com.fantasyapps.darmahealthcare.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.extensions.*
import com.fantasyapps.darmahealthcare.models.customer.CustomerModel
import com.fantasyapps.darmahealthcare.models.customerType.Data
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import com.fantasyapps.darmahealthcare.utils.DialogHelper
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_select_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SelectCustomerFragment : BaseFragment(), View.OnClickListener, AdapterView.OnItemSelectedListener, DialogAddCustomer.DataPassListener {
    private var tb_back: ImageView? = null
    private var tv_address: TextInputEditText? = null
    private var tv_email: TextInputEditText? = null
    private var ref_no: TextInputEditText? = null
    private var btnOk: Button? = null
    private var btnCancel: Button? = null
    private var getId = ""
    private var selectedName: String? = null
    private var getChoosedName = ""
    private val cusomerList = ArrayList<String>()
    private val finalList = ArrayList<String>()

    private var spinner: Spinner? = null
    private var addCustomer: LinearLayout? = null
    private val typelist = ArrayList<Data>()
    var types: List<String> = ArrayList()
    private var dialog: DialogHelper.Build? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_select_customer, container, false)
        tv_address = rootview.findViewById(R.id.address)
        tv_email = rootview.findViewById(R.id.email)
        btnOk = rootview.findViewById(R.id.btnok)
        btnCancel = rootview.findViewById(R.id.btnCancel)
        spinner = rootview.findViewById(R.id.spinner_search)
        addCustomer = rootview.findViewById(R.id.add_customer)
        ref_no = rootview.findViewById(R.id.reference_number)

        dialog = DialogHelper.with(context!!)

        val bundle = this.arguments
        if (bundle != null) {
            getId = bundle.getString("selectedID", "")
            Log.e("==> id : FromAdmin", getId)
            getChoosedName = bundle.getString("choosedName", "")
            Log.e("==> Name : FromAdmin", getChoosedName)
        }


        callCustomerApi()

        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, cusomerList)
        spinner!!.adapter = adapter
        spinner!!.setSelection(0)





        addCustomer!!.setOnClickListener(this)
        spinner!!.onItemSelectedListener = this
        btnCancel!!.setOnClickListener(this)
        btnOk!!.setOnClickListener(this)


        return rootview
    }

    private fun callCustomerApi() {
        dialog?.showProgress("Loading", "please wait...", false)

        val service = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        val call = service.allCustomer

        call.enqueue(object : Callback<CustomerModel> {
            override fun onResponse(call: Call<CustomerModel>, response: Response<CustomerModel>) {
                dialog?.hideProgress()
                info(this@SelectCustomerFragment, "$response")

                if (response.isSuccessful && response.code() == 200) {
                    val dataList = response.body()
                    if (dataList != null) {
                        if (dataList.data != null && dataList.data.customer.size > 0) {
                            cusomerList.clear()
                            for (i in 0 until dataList.data.customer.size) {
                                cusomerList.add(dataList.data.customer[i].name)
                            }
                        }
                    }

                } else {
                    context?.showShortToast("Something wrong!")
                }

            }

            override fun onFailure(call: Call<CustomerModel>, t: Throwable) {
                dialog?.hideProgress()
                context?.showDebugShortToast(t.localizedMessage)
            }
        })

    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btnok ->
                if (selectedName == null) {
                    context?.showShortToast("Please select the customer first")
                } else if (ref_no?.text.isNullOrBlank()) {
                    context?.showShortToast("Enter your reference number")
                } else {
                    OrderCreateFragment.C_REFERENCE = reference_number.text.toString()


                    val fragment = OrderCreateFragment.newInstance(0)
                    val bundle = fragment.arguments!!
                    bundle.putString("selectedName", selectedName)
                    bundle.putString("selectedID", getId)
                    fragment.arguments = bundle
                    mFragmentNavigation.pushFragment(fragment)
                }


            R.id.btnCancel -> {
                mFragmentNavigation.popFragment()
            }

            R.id.add_customer -> {
                val fragmentManager = activity!!.supportFragmentManager
                val requestDialogFragment = DialogAddCustomer()
                requestDialogFragment.setDialogFragmentEvents(this@SelectCustomerFragment)
                requestDialogFragment.show(fragmentManager, "request_dialog")
            }
        }

    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        //String item = (String) parent.getItemAtPosition(position);
        (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#ffffff"))
        getId = id.toString()
        selectedName = cusomerList[position]
        loadPrefilledData(selectedName)

        OrderCreateFragment.C_name = selectedName!!
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#ffffff"))
    }


    private fun loadPrefilledData(selectedName: String?) {
        dialog?.showProgress("Loading", "please wait...", false)

        val service = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        val call = service.allCustomer

        call.enqueue(object : Callback<CustomerModel> {
            override fun onResponse(call: Call<CustomerModel>, response: Response<CustomerModel>) {
                dialog?.hideProgress()
                info(this@SelectCustomerFragment, "$response")


                if (response.isSuccessful && response.code() == 200) {
                    val dataList = response.body()
                    if (dataList != null && dataList.data.customer.size > 0) {
                        for (i in 0 until dataList.data.customer.size) {
                            if (selectedName!!.equals(dataList.data.customer[i].name, ignoreCase = true)) {
                                tv_address!!.setText(dataList.data.customer[i].address)
                                tv_email!!.setText(dataList.data.customer[i].email)
                                OrderCreateFragment.C_id = dataList.data.customer[i].id.toString()
                                OrderCreateFragment.C_discountAmount = dataList.data.customer[i].discountAmount.toString()

                                if (!dataList.data.customer[i].discountBy.isNullOrEmpty()) {
                                    AddProductFragment.P_discountby = dataList.data.customer[i].discountBy
                                }
                            }
                        }
                    }
                } else {
                    context?.showShortToast("Something wrong!")
                }
            }

            override fun onFailure(call: Call<CustomerModel>, t: Throwable) {
                dialog?.hideProgress()
                error(this@SelectCustomerFragment , t.localizedMessage)
            }
        })

    }


    override fun passData(isSuccess: Boolean) {
        if (isSuccess) {
            val snackBar = Snackbar.make(activity!!.findViewById(R.id.activity_main), R.string.customer_added, Snackbar.LENGTH_LONG)
            snackBar.setAction("Ok") { snackBar.dismiss() }
            snackBar.show()
            callCustomerApi()

        } else {
            val snackBar = Snackbar.make(activity!!.findViewById(R.id.activity_main), R.string.failed, Snackbar.LENGTH_LONG)
            snackBar.setAction("Dismiss") { snackBar.dismiss() }
            snackBar.show()
        }
    }

    companion object {

        fun newInstance(instance: Int): SelectCustomerFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = SelectCustomerFragment()
            fragment.arguments = args
            return fragment

        }
    }

}
