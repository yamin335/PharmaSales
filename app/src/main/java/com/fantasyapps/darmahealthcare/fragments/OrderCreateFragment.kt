package com.fantasyapps.darmahealthcare.fragments


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.format.Time
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.MainActivity
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.adapters.ProductListAdapter
import com.fantasyapps.darmahealthcare.dialogs.CustomDialog
import com.fantasyapps.darmahealthcare.dialogs.ProgressDialog
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showDebugShortToast
import com.fantasyapps.darmahealthcare.extensions.showShortToast
import com.fantasyapps.darmahealthcare.listeners.YesNoCallback
import com.fantasyapps.darmahealthcare.models.SubmitOrder.OrderSubmitResponse
import com.fantasyapps.darmahealthcare.models.SubmitOrder.raw.Product
import com.fantasyapps.darmahealthcare.models.SubmitOrder.raw.SendOrderModel
import com.fantasyapps.darmahealthcare.models.custom.ProductDetails
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.*

class OrderCreateFragment : BaseFragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    internal var productRecyclerView: RecyclerView? = null
    internal var adapter: ProductListAdapter? = null
    private var tvChoose: TextView? = null
    private var tvAddProduct: TextView? = null
    private var tvSubtotal: TextView? = null
    private var tvOrderNumber: TextView? = null
    private var tvTotal: TextView? = null
    private var discount: EditText? = null
    private var tax: EditText? = null
    private val getName: String? = null
    private val getId = ""
    private val list = ArrayList<ProductDetails>()
    private var up_subtotal: Double? = null
    private val total: Double? = null
    private val calc_discount: Double? = null
    private var calc_total: Double? = null
    private var btnSubmit: Button? = null
    private var btnCancel: Button? = null
    private var spinner_discount: Spinner? = null
    private var spinner_tax: Spinner? = null
    private var submitLayout:LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        tvOrderNumber = view.findViewById(R.id.sales_order_number)
        tvChoose = view.findViewById(R.id.tv_choose_customer)
        tvAddProduct = view.findViewById(R.id.tv_add_product)
        tvSubtotal = view.findViewById(R.id.subtotal)
        discount = view.findViewById(R.id.edt_discount)
        tax = view.findViewById(R.id.edt_tax)
        tvTotal = view.findViewById(R.id.tv_total)
        btnSubmit = view.findViewById(R.id.btn_submit)
        btnCancel = view.findViewById(R.id.btn_cancel)
        spinner_discount = view.findViewById(R.id.discount_dropdown)
        spinner_tax = view.findViewById(R.id.tax_dropdown)
        submitLayout = view.findViewById(R.id.submit_layout)

        spinner_discount!!.isEnabled = false


        productRecyclerView = view.findViewById(R.id.recycler_product_list)
        productRecyclerView!!.setHasFixedSize(false)
        productRecyclerView!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        productRecyclerView!!.itemAnimator = itemAnimator

        setOrderNumber()

        btnCancel?.setOnClickListener(this)
        spinner_discount!!.onItemSelectedListener = this
        btnSubmit!!.setOnClickListener(this)
        tvChoose!!.setOnClickListener {

            val fragment = SelectCustomerFragment.newInstance(0)
            mFragmentNavigation.pushFragment(fragment)
        }


        tvAddProduct?.setOnClickListener {
            if (tvChoose!!.text == "Choose customer") {
                Toast.makeText(context, "Please choose customer first", Toast.LENGTH_SHORT).show()
            } else {

                val fragment = AddProductFragment.newInstance(0)
                mFragmentNavigation.pushFragment(fragment)


            }
        }


        if (!C_name.equals("", ignoreCase = true)) {
            //Toast.makeText(getContext(),C_name, Toast.LENGTH_SHORT).show();
            tvChoose!!.text = C_name

        } else {
            tvChoose!!.text = "Choose customer"
        }

        if (C_discountAmount != "") {
            discount!!.setText(C_discountAmount)
        }

        if (P_list!!.size > 0) {

            // Toast.makeText(getContext(), P_list.get(0).getpName(), Toast.LENGTH_SHORT).show();
            val finalList = ArrayList<ProductDetails>()
            finalList.addAll(P_list!!)

            adapter = ProductListAdapter(finalList, context!!, object : ProductListAdapter.AdapterCallback {
                override fun onMethodCallback(subTotal: Double?) {
                    if (subTotal != 0.0) {
                        val result = calulateTotal(subTotal, discount, tax)
                        tvTotal!!.text = doubleToString(result)
                        tvSubtotal!!.text = doubleToString(subTotal)
                    } else {
                        tvSubtotal!!.text = doubleToString(subTotal)
                        if (C_name != "") {
                            discount!!.setText(C_discountAmount)
                        } else {
                            discount!!.setText(null)
                        }

                        tax!!.setText(null)
                        tvTotal!!.text = doubleToString(subTotal)
                    }
                }
            })
            productRecyclerView!!.adapter = adapter
            adapter!!.notifyDataSetChanged()


            up_subtotal = 0.0
            for (i in finalList.indices) {
                val amount = finalList[i].getpAmount()
                up_subtotal = up_subtotal!! + amount!!
            }


            discount!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    val current_subtotal = java.lang.Double.valueOf(tvSubtotal!!.text.toString())
                    calc_total = calulateTotal(current_subtotal, discount, tax)
                    tvTotal!!.text = doubleToString(calc_total)

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    val current_subtotal = java.lang.Double.valueOf(tvSubtotal!!.text.toString())
                    calc_total = calulateTotal(current_subtotal, discount, tax)
                    tvTotal!!.text = doubleToString(calc_total)
                }
            })

            tax!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    val current_subtotal = java.lang.Double.valueOf(tvSubtotal!!.text.toString())
                    calc_total = calulateTotal(current_subtotal, discount, tax)
                    tvTotal!!.text = doubleToString(calc_total)
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    val current_subtotal = java.lang.Double.valueOf(tvSubtotal!!.text.toString())
                    calc_total = calulateTotal(current_subtotal, discount, tax)
                    tvTotal!!.text = doubleToString(calc_total)

                }
            })


            tvSubtotal!!.text = doubleToString(up_subtotal)
            tvTotal!!.text = doubleToString(up_subtotal)

        }

        return view
    }

    fun calulateTotal(subtotal: Double?, discount: EditText?, tax: EditText?): Double? {
        var c_disc: Double? = 0.0
        var c_tax: Double? = 0.0
        var calc_disc: Double? = 0.0
        var final_calc: Double? = 0.0
        if (spinner_discount!!.selectedItemId == 0L) {
            if (!isEditTextEmpty(discount)) {
                c_disc = java.lang.Double.valueOf(discount!!.text.toString()) / 100.0

            }
            if (!isEditTextEmpty(tax)) {
                c_tax = java.lang.Double.valueOf(tax!!.text.toString()) / 100.0
            }

            calc_disc = subtotal!! - subtotal * c_disc!!
            final_calc = calc_disc + subtotal * c_tax!!

        } else if (spinner_discount!!.selectedItemId == 1L) {
            if (!isEditTextEmpty(discount)) {
                c_disc = java.lang.Double.valueOf(discount!!.text.toString())

            }
            if (!isEditTextEmpty(tax)) {
                c_tax = java.lang.Double.valueOf(tax!!.text.toString()) / 100.0
            }

            calc_disc = subtotal!! - c_disc!!
            final_calc = calc_disc + subtotal * c_tax!!
        }

        return final_calc
    }

    private fun setOrderNumber() {
        val time = Time()
        time.setToNow()
//      Log.d("TIME TEST", "SO-" + java.lang.Long.toString(time.toMillis(false)))
        orderNum = "SO-" + java.lang.Long.toString(time.toMillis(false))
        tvOrderNumber!!.text = orderNum
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_submit ->
                if (tvChoose!!.text == "Choose customer") {
                    Toast.makeText(context, "Please choose customer first", Toast.LENGTH_SHORT).show()
                } else if (P_list!!.size == 0) {
                    Toast.makeText(context, "Please select at least one product", Toast.LENGTH_SHORT).show()
                } else {
                    sendOrderToServer()
                }


            R.id.btn_cancel -> {

                if (P_list?.size != 0 && C_name != "") {
                    val dialog = CustomDialog()
                    dialog.showDialog(activity, "Order Cancel", "Are you sure ?", object : YesNoCallback {
                        override fun onYes() {
                            if (P_list != null) {
                                P_list?.clear()
                            }
                            C_id = ""
                            C_name = ""
                            C_discountAmount = ""
                            Toast.makeText(context, "Order cancelled", Toast.LENGTH_SHORT).show()

                            MainActivity.start(context!!)
                            activity!!.finish()
                        }

                        override fun onNo() {

                        }
                    })
                } else {
                    Toast.makeText(context, "Please make an order first", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendOrderToServer() {
        val progress = ProgressDialog()
        progress.showProgressDialog(activity!!, "Please wait", false)

        val preferences = context!!.getSharedPreferences("loggedIn", Context.MODE_PRIVATE)
        var salesman_id = preferences.getLong("id", -1L)
        var branch_id = preferences.getInt("branchId", -1)



        val productList = ArrayList<Product>()
        for (i in P_list!!.indices) {
            productList.add(Product(P_list!![i].getpName(), P_list!![i].getpDesc(), P_list!![i].getpPrice(), P_list!![i].getpQty(), P_list!![i].getpAmount(), P_list!![i].getpName(), P_list!![i].getpId()))
        }



        try {
            val json = SendOrderModel()
            json.customerId = C_id
            json.salesmanId = salesman_id.toInt()
            json.branchId = branch_id
            json.subtotal = tvSubtotal!!.text.toString()
            json.yourReference = C_REFERENCE
            json.ourReference = tvOrderNumber!!.text.toString()

            if (spinner_discount!!.selectedItemId == 0L) {
                json.discountType = "percentage"
            } else if (spinner_discount!!.selectedItemId == 1L) {
                json.discountType = "fixed"
            }

            if (!TextUtils.isEmpty(discount!!.text.toString())) {
                json.discount = Integer.parseInt(discount!!.text.toString())
            }

            if (!TextUtils.isEmpty(tax!!.text.toString())) {
                json.tax = Integer.parseInt(tax!!.text.toString())
            }

            json.products = productList
            json.grandTotal = tvTotal!!.text.toString()

            val apiservice = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java!!)
            val call = apiservice.postOrderRawJSON(json)
            call.enqueue(object : retrofit2.Callback<OrderSubmitResponse> {
                override fun onResponse(call: Call<OrderSubmitResponse>, response: Response<OrderSubmitResponse>) {
                    progress.stopProgressDialog()
                    info(this@OrderCreateFragment , "$response")
                    if (response.isSuccessful && response.code() == 200) {
                        val sucessData = response.body()
                        if (sucessData != null){
                            if (sucessData.status.equals("success")){
                                context?.showShortToast("Order created successfully")
                                if (P_list != null) {
                                    P_list!!.clear()
                                }
                                C_id = ""
                                C_name = ""
                                C_REFERENCE = ""

                                MainActivity.start(context!!)
                                activity!!.finish()
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<OrderSubmitResponse>, t: Throwable) {
                    //hideProgress();
                    progress.stopProgressDialog()
                    context?.showDebugShortToast(t.localizedMessage)
                    error(this@OrderCreateFragment , t.localizedMessage)
                }
            })
        } catch (e: Exception) {
            progress.stopProgressDialog()
            error(this@OrderCreateFragment , e.localizedMessage)
        }

    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == spinner_discount?.selectedItemPosition) {
            if (!tvSubtotal?.text.isNullOrEmpty()){
                val current_subtotal = java.lang.Double.valueOf(tvSubtotal!!.text.toString())
                calc_total = calulateTotal(current_subtotal, discount, tax)
                tvTotal?.text = doubleToString(calc_total)
            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    companion object {

        fun newInstance(instance: Int): OrderCreateFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = OrderCreateFragment()
            fragment.arguments = args
            return fragment

        }

        var C_name = ""
        var C_id = ""
        var C_discountAmount = ""
        var C_REFERENCE = ""
        var P_list: MutableList<ProductDetails>? = ArrayList()
        var orderNum: String? = null

        fun isEditTextEmpty(str: EditText?): Boolean {
            if (str == null)
                return true
            return if (TextUtils.isEmpty(str.text.toString())) true else false
        }

        fun doubleToString(input: Double?): String {
            return input.toString()
        }
    }
}

//    @Override
//    public void onClick(ProductDetails details) {
//        //Toast.makeText(getContext(), details.getpName(), Toast.LENGTH_SHORT).show();
//    }

