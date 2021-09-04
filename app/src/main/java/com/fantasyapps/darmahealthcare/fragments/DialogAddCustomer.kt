package com.fantasyapps.darmahealthcare.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showShortToast
import com.fantasyapps.darmahealthcare.models.AddCustomer.raw.CustomerRawModel
import com.fantasyapps.darmahealthcare.models.AddCustomer.sucessResponse.SuccessResponseModel
import com.fantasyapps.darmahealthcare.models.customerType.CustomerType
import com.fantasyapps.darmahealthcare.models.customerType.CustomerTypeModel
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import com.fantasyapps.darmahealthcare.utils.DialogHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DialogAddCustomer : DialogFragment(), CompoundButton.OnCheckedChangeListener {
    private val typelist = ArrayList<CustomerType>()
    private val types = ArrayList<String>()

    private var btnSend: TextView? = null
    private var btnCancel: TextView? = null
    private var name: EditText? = null
    private var address: EditText? = null
    private var city: EditText? = null
    private var state: EditText? = null
    private var zip: EditText? = null
    private var phone: EditText? = null
    private var email: EditText? = null
    private var contact_person: EditText? = null
    private var discount_amount: EditText? = null
    private var spinnerTypes: Spinner? = null
    private var checkBox_discount: CheckBox? = null
    private var discount_field: LinearLayout? = null
    private var radioDiscountGroup: RadioGroup? = null
    private var radioSelectedButton: RadioButton? = null

    internal lateinit var mCallback: DataPassListener
    private var dialog:DialogHelper.Build? = null
    private var scrollview:ScrollView? = null


    private val customerTypeList: List<CustomerType>
        get() {
            typelist.clear()
            val service = RetrofitBuilder.buildRetrofit().create(ApiService::class.java)
            val call = service.allTypes
            call.enqueue(object : Callback<CustomerTypeModel> {
                override fun onResponse(call: Call<CustomerTypeModel>, response: Response<CustomerTypeModel>) {
                        info(this@DialogAddCustomer , "$response")
                        if (response.isSuccessful && response.code() == 200) {
                            val dataList = response.body()
                            if (dataList != null && dataList.data.customerType.size > 0){
                                for (i in 0 until dataList.data.customerType.size) {
                                    typelist.add(CustomerType(dataList.data.customerType[i].name, dataList.data.customerType[i].id))
                                }
                            }

                        }

                        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, typelist)
                        spinnerTypes!!.adapter = adapter
                        spinnerTypes!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                spinnerTypes!!.setSelection(position)
                                val data = parent.selectedItem as CustomerType
                                val name = data.name
                                val uid = data.id

//                                context?.showShortToast("$name id :$uid")
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
                        }


                }

                override fun onFailure(call: Call<CustomerTypeModel>, t: Throwable) {
                    error(this@DialogAddCustomer , t.localizedMessage)

                }
            })

            return typelist
        }

    private val selectedID: Long
        get() {
            try {
                val data = spinnerTypes?.selectedItem as CustomerType
                return data.id
            }catch (ex:Exception){
                error(this@DialogAddCustomer , ex.localizedMessage)
            }

            return -1L
        }

    fun setDialogFragmentEvents(mCallback: DataPassListener) {
        this.mCallback = mCallback
    }


    interface DataPassListener {
        fun passData(isSuccess: Boolean)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_add_customer, container, false)
        btnSend = rootView.findViewById(R.id.send)
        btnCancel = rootView.findViewById(R.id.cancel)
        name = rootView.findViewById(R.id.customer_name)
        address = rootView.findViewById(R.id.customer_address)
        city = rootView.findViewById(R.id.customer_city)
        state = rootView.findViewById(R.id.customer_state)
        zip = rootView.findViewById(R.id.customer_zipcode)
        phone = rootView.findViewById(R.id.customer_phone)
        email = rootView.findViewById(R.id.customer_email)
        contact_person = rootView.findViewById(R.id.customer_contact_person)
        discount_amount = rootView.findViewById(R.id.customer_discount_amount)
        spinnerTypes = rootView.findViewById(R.id.spinner_type)
        radioDiscountGroup = rootView.findViewById(R.id.radioGroup_discount)
        checkBox_discount = rootView.findViewById(R.id.checkbox_discount)
        discount_field = rootView.findViewById(R.id.discount_field)

        scrollview = rootView.findViewById<ScrollView>(R.id.scrollview_addcustomer)

        dialog = DialogHelper.with(context!!)


        customerTypeList

        checkBox_discount!!.setOnCheckedChangeListener(this)
        btnSend!!.setOnClickListener {

            if (name?.text.isNullOrEmpty()){
                name!!.setError("field cannot be empty")
            }else if (address?.text.isNullOrEmpty()){
                address!!.setError("field cannot be empty")
            }else if (city?.text.isNullOrEmpty()){
                city!!.setError("field cannot be empty")
            }else if (state?.text.isNullOrEmpty()){
                state!!.setError("field cannot be empty")
            }else if (zip?.text.isNullOrEmpty()){
                zip!!.setError("field cannot be empty")
            }else if (phone?.text.isNullOrEmpty()){
                phone!!.setError("field cannot be empty")
            }else if (email?.text.isNullOrEmpty()){
                email!!.setError("field cannot be empty")
            }else if (contact_person?.text.isNullOrEmpty()){
                contact_person!!.setError("field cannot be empty")
            }else{
                dismiss()

                val selectedId = radioDiscountGroup?.checkedRadioButtonId
                radioSelectedButton = rootView.findViewById(selectedId!!)
                val matchedString = radioSelectedButton?.text.toString()
                postCustomerData(matchedString)
            }

        }

        /*else if(radioDiscountGroup?.checkedRadioButtonId == -1){
            context?.showShortToast("Please select discount type")
        }
*/
        btnCancel!!.setOnClickListener { dismiss() }

        return rootView
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            scrollview?.postDelayed(Runnable {
                scrollview?.fullScroll(ScrollView.FOCUS_DOWN);
            }, 300)

            discount_field!!.visibility = View.VISIBLE
        } else {
            discount_field!!.visibility = View.GONE
        }
    }


    private fun postCustomerData(matchedString: String) {

        dialog?.showProgress("", "Please wait...", false)

        val json = CustomerRawModel()
        json.name = name?.text.toString()
        json.address = address?.text.toString()
        json.city = city?.text.toString()
        json.state = state?.text.toString()
        json.zipcode = zip?.text.toString()
        json.phone = phone?.text.toString()
        json.email = email?.text.toString()
        json.contactPerson = contact_person?.text.toString()
        json.customerTypeId = selectedID
        error(this@DialogAddCustomer , selectedID.toString())

        if (checkBox_discount!!.isChecked) {
            error(this@DialogAddCustomer , matchedString)
            if (matchedString == "TP") {
                json.discountBy = "selling_price"
            } else if (matchedString == "MRP") {
                json.discountBy = "mrp"
            }

            if (TextUtils.isEmpty(discount_amount?.text.toString())) {
                json.discountAmount = 0.0
            } else {
                json.discountAmount = java.lang.Double.valueOf(discount_amount?.text.toString())
            }

            json.giveDiscount = "true"
        } else {
            json.discountAmount = 0.0
            json.discountBy = "selling_price"
            json.giveDiscount = "false"
        }


        val apiservice = RetrofitBuilder.buildRetrofit().create(ApiService::class.java)
        val call = apiservice.postCustomerRawJSON(json)
        call.enqueue(object : Callback<SuccessResponseModel> {
            override fun onResponse(call: Call<SuccessResponseModel>, response: Response<SuccessResponseModel>) {
                //hideProgress();
                dialog?.hideProgress()
                info(this@DialogAddCustomer , "$response")
                if (response.isSuccessful) {
                    if (response.code() == 201){

                    }else if (response.code() == 200) {
                        val sucessdata = response.body()
                        if (sucessdata != null && sucessdata.status.equals("success" , ignoreCase = true)){
                            mCallback.passData(true)
                        }
                    }else if (response.code() == 500) {
                        mCallback.passData(false)
                    }
                }
            }

            override fun onFailure(call: Call<SuccessResponseModel>, t: Throwable) {
                dialog?.hideProgress()
                mCallback.passData(false)
                error(this@DialogAddCustomer , t.localizedMessage)
            }
        })

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // request a window without the title
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.dialog_rounded)
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
        val window = getDialog().window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }


}
