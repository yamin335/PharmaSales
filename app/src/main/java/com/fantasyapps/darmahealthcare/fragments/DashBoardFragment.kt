package com.fantasyapps.darmahealthcare.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.adapters.DashBoardAdapter
import com.fantasyapps.darmahealthcare.extensions.debug
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showShortToast
import com.fantasyapps.darmahealthcare.models.SalesModule.SalesData
import com.fantasyapps.darmahealthcare.models.SalesModule.SalesModelList
import com.fantasyapps.darmahealthcare.models.SalesModule.rawJson.FilterDate
import com.fantasyapps.darmahealthcare.models.SalesModule.rawJson.RawSalesModel
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import com.fantasyapps.darmahealthcare.utils.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DashBoardFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    internal var dashBoardRecyclerView: RecyclerView? = null
    internal var dashBoardAdapter: DashBoardAdapter? = null
    //DashBoardAdapter adapter;
    internal var linearLayoutManager: LinearLayoutManager? = null
    //private Integer lastPage, currentPage, totalitem;
    //boolean isLoading = false;
    internal var rv: RecyclerView? = null
    internal var progressBar: ProgressBar? = null
    private val list = ArrayList<SalesData>()
    var islastPage: Boolean = false
    var isloading: Boolean = false
    private var TOTAL_PAGES = 1
    private var currentPage = PAGE_START
    private var apiService: ApiService? = null
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var layoutManager: LinearLayoutManager? = null
    private var errorLoadingLayout: LinearLayout? = null
    private var id: Long? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dash_board, container, false)
        dashBoardRecyclerView = rootView.findViewById(R.id.recycler_dashboard)
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container)
        errorLoadingLayout = rootView.findViewById(R.id.layout_loader)
        refreshLayout = rootView.findViewById(R.id.swipeContainer_dashboard)
        refreshLayout!!.setOnRefreshListener(this)
        refreshLayout!!.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        val preferences = context!!.getSharedPreferences("loggedIn", Context.MODE_PRIVATE)
        id = preferences.getLong("id", -1L)


        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        dashBoardRecyclerView!!.layoutManager = layoutManager
        val itemAnim = DefaultItemAnimator()
        itemAnim.addDuration = 1000
        itemAnim.removeDuration = 1000
        dashBoardRecyclerView!!.itemAnimator = itemAnim
        dashBoardRecyclerView!!.adapter = dashBoardAdapter


        //init service and load data
        apiService = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        loadFirstPage()
        dashBoardRecyclerView?.addOnScrollListener(object : PaginationScrollListener(layoutManager!!) {
            override val totalPageCount: Int
                get() = TOTAL_PAGES
            override val isLastPage: Boolean
                get() = islastPage
            override val isLoading: Boolean
                get() = isloading

            override fun loadMoreItems() {
                isloading = true
                currentPage +=1
                Handler().postDelayed({ loadNextPage() }, 1000)


            }

        })




        return rootView
    }


    override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        mShimmerViewContainer!!.stopShimmerAnimation()
        super.onPause()
    }

    private fun loadFirstPage() {
        debug(this@DashBoardFragment , "loadFirstPage: ")

        callSalesModelApi().enqueue(object : Callback<SalesModelList> {
            override fun onResponse(call: Call<SalesModelList>, response: Response<SalesModelList>) {
                // Got data. Send it to adapter

                if (response.isSuccessful && response.code() == 200) {
                    val salesModelList = response.body()
                    if (salesModelList != null){
                        if (salesModelList.status.equals("success")){
                            if (salesModelList.data.sales.salesData != null){
                                for (i in 0 until salesModelList.data.sales.salesData.size) {
                                    list.add(salesModelList.data.sales.salesData[i])
                                }
                            }


                            TOTAL_PAGES = salesModelList.data.sales.lastPage

                            dashBoardAdapter = DashBoardAdapter(activity, list)
                            dashBoardRecyclerView!!.adapter = dashBoardAdapter
                            dashBoardAdapter!!.notifyDataSetChanged()

                            if (currentPage != TOTAL_PAGES)
                                dashBoardAdapter!!.addLoadingFooter()
                            else
                                islastPage = true

                            // Stopping Shimmer Effect's animation after data is loaded to ListView
                            mShimmerViewContainer!!.stopShimmerAnimation()
                            mShimmerViewContainer!!.visibility = View.GONE
                            if (errorLoadingLayout!!.visibility == View.VISIBLE){
                                errorLoadingLayout!!.visibility = View.GONE
                            }
                            refreshLayout!!.isRefreshing = false
                        }
                    }

                } else {
                    context?.showShortToast("Something wrong")
                }
            }

            override fun onFailure(call: Call<SalesModelList>, t: Throwable) {
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE
                errorLoadingLayout!!.visibility = View.VISIBLE
                refreshLayout!!.isRefreshing = false

                debug(this@DashBoardFragment , t.localizedMessage)
                context?.showShortToast(t.localizedMessage)
            }
        })

    }

    private val date: String
        get() {
            //val df = android.text.format.DateFormat()
            val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
            error(this@DashBoardFragment , date)
            return date
        }

    private fun getCurrentAndLastDate(): String{
        val calendar = Calendar.getInstance();
        calendar.setTime(Date());

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        val lastDayOfMonth = calendar.getTime();
        val sdf = SimpleDateFormat("yyyy-MM-dd");
        info(this@DashBoardFragment , "Today " + sdf.format(Date()))
        info(this@DashBoardFragment , "Last Day of Month " + sdf.format(lastDayOfMonth))

        val result = sdf.format(Date()) + "," + sdf.format(lastDayOfMonth)
        return result
    }

    private fun callSalesModelApi(): Call<SalesModelList> {

        val rawJson = RawSalesModel()
        val filterDate = FilterDate()
        val date = getCurrentAndLastDate()
        val str = date.split(",");
        filterDate.startDate = str[0]
        filterDate.endDate = str[1]



        rawJson.column = "salesman_id"
        rawJson.data = id!!
        rawJson.isIsDue = false
        rawJson.filterDate = filterDate

        return apiService!!.getSalesData(rawJson)
    }

    private fun loadNextPage() {
        error(this@DashBoardFragment , "loadNextPage: $currentPage")

        callSalesModelApi().enqueue(object : Callback<SalesModelList> {
            override fun onResponse(call: Call<SalesModelList>, response: Response<SalesModelList>) {
                dashBoardAdapter!!.removeLoadingFooter()
                isloading = false

                //List<SalesModel> results = fetchResults(response);
                val salesModelList = response.body()
                if (salesModelList != null){
                    for (i in 0 until salesModelList.data.sales.salesData.size) {
                        list.add(salesModelList.data.sales.salesData[i])
                    }


                    dashBoardAdapter = DashBoardAdapter(activity, list)
                    dashBoardRecyclerView!!.adapter = dashBoardAdapter
                    dashBoardAdapter!!.notifyDataSetChanged()
                    //adapter.addAll(list);

                    if (currentPage != TOTAL_PAGES)
                        dashBoardAdapter!!.addLoadingFooter()
                    else
                        islastPage = true
                }

            }

            override fun onFailure(call: Call<SalesModelList>, t: Throwable) {
                error(this@DashBoardFragment , t.localizedMessage)
            }
        })
    }


    override fun onRefresh() {
        mShimmerViewContainer!!.visibility = View.VISIBLE
        mShimmerViewContainer!!.startShimmerAnimation()
        refreshLayout!!.isRefreshing = true
        if (errorLoadingLayout!!.visibility == View.VISIBLE){
            errorLoadingLayout!!.visibility = View.GONE
        }
        if (dashBoardAdapter != null) {
            dashBoardAdapter!!.clear()
            dashBoardAdapter!!.notifyDataSetChanged()
        }

        Handler().postDelayed({ loadFirstPage() }, 2000)

    }

    companion object {
        private val PAGE_START = 1

        fun newInstance(instance: Int): DashBoardFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = DashBoardFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
