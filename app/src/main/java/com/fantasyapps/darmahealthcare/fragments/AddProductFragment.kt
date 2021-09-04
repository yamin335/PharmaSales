package com.fantasyapps.darmahealthcare.fragments

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.MainActivity
import com.fantasyapps.darmahealthcare.MainActivity.Companion.INDEX_ADD_PRODUCT
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showDebugShortToast
import com.fantasyapps.darmahealthcare.models.custom.ProductDetails
import com.fantasyapps.darmahealthcare.models.product.AddProductModelList
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import com.fantasyapps.darmahealthcare.utils.DialogHelper
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AddProductFragment : BaseFragment(), View.OnClickListener {
    private var tb_back: ImageView? = null
    private var tvProduct: TextView? = null
    private var spinnerDialog: SpinnerDialog? = null
    private var selectedProduct: String? = null
    private val productList = ArrayList<String>()
    private val plist = ArrayList<ProductDetails>()

    private var p_desc: TextView? = null
    private var p_quantity: TextInputEditText? = null
    private var p_price: TextInputEditText? = null
    private var p_amount: TextInputEditText? = null
    private var btnUpdate: Button? = null
    private var btnAdd: Button? = null
    private var qty = 0
    private var price: Double? = 0.0
    private var amount: Double? = 0.0
    private var pId: Long = 0L
    private var desc: String? = null
    private var productName: String? = null
    private var dialog: DialogHelper.Build? = null

    private val amountWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //textView.setVisibility(View.VISIBLE);
        }

        override fun afterTextChanged(s: Editable) {
            if (s.length == 0) {
//                p_price!!.setText("0")
                p_amount!!.setText("0")
            } else {
                if (!TextUtils.isEmpty(p_price?.text.toString())) {
                    price = java.lang.Double.valueOf(p_price?.text.toString())
                    qty = Integer.valueOf(p_quantity?.text.toString())
                    amount = price!! * qty
                    p_amount?.setText(amount.toString())
                }
            }
        }
    }

    private val priceWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //btnAdd.setEnabled(false);
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (s.length == 0) {
                p_amount?.text = null
            } else {
                if (!TextUtils.isEmpty(p_price?.text?.toString())) {
                    price = java.lang.Double.valueOf(p_price?.text.toString())
                    if (!TextUtils.isEmpty(p_quantity?.text.toString())) {
                        qty = Integer.valueOf(p_quantity?.text.toString())
                    } else {
                        qty = 0
                    }

                    amount = price!! * qty
                    p_amount?.setText(amount.toString())
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_add_product, container, false)
        tvProduct = rootview.findViewById(R.id.spinner_search_product)
        p_desc = rootview.findViewById(R.id.product_add_description)
        p_price = rootview.findViewById(R.id.product_add_price)
        p_amount = rootview.findViewById(R.id.product_add_amount)
        p_quantity = rootview.findViewById(R.id.product_add_quantity)
        btnAdd = rootview.findViewById(R.id.btnAdd)

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog = DialogHelper.with(context!!)

        tvProduct?.setOnClickListener(this)
        p_quantity?.addTextChangedListener(amountWatcher)
        p_price?.addTextChangedListener(priceWatcher)
        btnAdd?.setOnClickListener(this)

        inItData()

        return rootview
    }

    private fun inItData() {
        productName = tvProduct?.text.toString()
        desc = p_desc?.text.toString()
        //result = p_price.getText().toString();


        callProductApi()
        spinnerDialog = SpinnerDialog(activity, productList, "Select or Search Product")
        spinnerDialog?.setCancellable(false)
        spinnerDialog?.setShowKeyboard(false)
        spinnerDialog?.bindOnSpinerListener { item, position ->
            //Toast.makeText(getContext(), item + "  " + position + "", Toast.LENGTH_SHORT).show();
            productName = item
            selectedProduct = productName
            tvProduct?.text = selectedProduct
            loadPrefilledData(selectedProduct)
            if (qty != 0) {
                p_quantity!!.text = null
            }
        }
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.spinner_search_product -> spinnerDialog?.showSpinerDialog()

            R.id.btnAdd -> if (tvProduct?.text == "") {
                Toast.makeText(context, "Please select your product first!", Toast.LENGTH_SHORT).show()
            } else if (qty == 0) {
                Toast.makeText(context, "Input at least one quatity!", Toast.LENGTH_SHORT).show()
            } else {

                OrderCreateFragment.P_list?.add(ProductDetails(productName, desc, price, qty, amount, pId))
                MainActivity.start(context!!, INDEX_ADD_PRODUCT)
                activity!!.finish()

            }
        }

    }


    private fun callProductApi() {

        dialog?.showProgress("", "Please wait...", false)


        val service = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        val call = service.allProducts

        call.enqueue(object : Callback<AddProductModelList> {
            override fun onResponse(call: Call<AddProductModelList>, response: Response<AddProductModelList>) {
                dialog?.hideProgress()
                info(this@AddProductFragment, "$response")

                if (response.isSuccessful && response.code() == 200) {
                    val datalist = response.body()
                    if (datalist != null && datalist.data.product.size > 0) {
                        productList.clear()
                        for (i in 0 until datalist.data.product.size) {
                            productList.add(datalist.data.product[i].name)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<AddProductModelList>, t: Throwable) {
                dialog?.hideProgress()
                error(this@AddProductFragment, t.localizedMessage)
            }
        })

    }

    private fun loadPrefilledData(selectedProduct: String?) {

        dialog?.showProgress("Loading", "Please wait...", false)

        val service = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        val call = service.allProducts

        call.enqueue(object : Callback<AddProductModelList> {
            override fun onResponse(call: Call<AddProductModelList>, response: Response<AddProductModelList>) {
                dialog?.hideProgress()
                info(this@AddProductFragment, "$response")

                if (response.isSuccessful && response.code() == 200) {
                    val dataList = response.body()
                    if (dataList != null && dataList.data.product.size > 0){
                        for (i in 0 until dataList.data.product.size) {
                            if (selectedProduct!!.equals(dataList.data.product[i].name, ignoreCase = true)) {
                                p_desc!!.text = dataList.data.product[i].description
                                desc = p_desc!!.text.toString()
                                pId = dataList.data.product[i].id
                                p_quantity!!.setText("1")


                                if (P_discountby.equals("mrp", ignoreCase = true)) {
                                    p_price?.setText(dataList.data.product[i].mrp.toString())

                                } else if (P_discountby.equals("selling_price", ignoreCase = true)) {
                                    p_price!!.setText(dataList.data.product[i].sellingPrice.toString())

                                } else if (P_discountby.equals("buying_price", ignoreCase = true)) {
                                    p_price!!.setText(dataList.data.product[i].buyingPrice.toString())
                                }

                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<AddProductModelList>, t: Throwable) {
                dialog?.hideProgress()
                error(this@AddProductFragment , t.localizedMessage)
                context?.showDebugShortToast(t.localizedMessage)
            }
        })

    }

    companion object {

        var P_discountby = ""

        fun newInstance(instance: Int): AddProductFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = AddProductFragment()
            fragment.arguments = args
            return fragment

        }
    }
}
