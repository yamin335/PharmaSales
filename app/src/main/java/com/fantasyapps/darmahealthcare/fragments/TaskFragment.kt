package com.fantasyapps.darmahealthcare.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fantasyapps.darmahealthcare.BaseFragment
import com.fantasyapps.darmahealthcare.BuildConfig
import com.fantasyapps.darmahealthcare.R
import com.fantasyapps.darmahealthcare.Services.LocationRequestHelper
import com.fantasyapps.darmahealthcare.Services.LocationResultHelper
import com.fantasyapps.darmahealthcare.Services.LocationUpdatesBroadcastReceiver
import com.fantasyapps.darmahealthcare.adapters.PunchDetailsAdapter
import com.fantasyapps.darmahealthcare.dialogs.ProgressDialog
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showShortToast
import com.fantasyapps.darmahealthcare.models.PuchInOut.PunchInOutResponse
import com.fantasyapps.darmahealthcare.models.PuchInOut.PunchRawModel
import com.fantasyapps.darmahealthcare.models.PunchDetails.PunchDetailsList
import com.fantasyapps.darmahealthcare.models.PunchDetails.PunchDetailsModel
import com.fantasyapps.darmahealthcare.models.PunchReqRaw.PunchReqModel
import com.fantasyapps.darmahealthcare.models.shift.Shift
import com.fantasyapps.darmahealthcare.models.shift.ShiftModel
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder
import com.fantasyapps.darmahealthcare.utils.MyBounchInterpolator
import com.fantasyapps.darmahealthcare.utils.MyPreference
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TaskFragment : BaseFragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
                     SharedPreferences.OnSharedPreferenceChangeListener,
                     GoogleApiClient.ConnectionCallbacks,
                     GoogleApiClient.OnConnectionFailedListener {


    private var mLocationRequest: LocationRequest? = null
    private var mGoogleApiClient: GoogleApiClient? = null


    internal lateinit var recyclerView: RecyclerView
    internal var punchDetailsAdapter: PunchDetailsAdapter? = null
    private var puncIn: Button? = null
    private var punchOut: Button? = null
    private var puchInOutView: LinearLayout? = null
    private var spinnerShift: Spinner? = null
    private val shifList = ArrayList<Shift>()
    private val ShiftId: Long? = null
    private var preference: MyPreference? = null
    private val punchDetailsLists = ArrayList<PunchDetailsModel>()
    private val s_id: Long = 0
    private var empId = "0f0f0f"
    private var rootView: View? = null
    internal var mcontext: Context? = null
    //internal var googleApiHelper: GoogleApiHelper? = null
    private var swipeTaskLayout: SwipeRefreshLayout? = null
    private var isLoading = false


    private val dateTime: String
        get() {
            val df = android.text.format.DateFormat()
            val date = SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(Date())
//            Log.e("Date & Time", date)
            return date
        }

    private val date: String
        get() {
            //val df = android.text.format.DateFormat()
            val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
//            Log.e("Date", date)
            return date
        }

    private val selectedID: Long
        get() {

            var data: Shift? = null
            if (spinnerShift != null) {
                try {
                    var data = spinnerShift!!.selectedItem as? Shift
                    return data!!.id
                }
                catch (e: TypeCastException) {
                    error(this@TaskFragment, e.localizedMessage)
                }
            }

            return data!!.id
        }

    private val pendingIntent: PendingIntent
        get() {
            val intent = Intent(mcontext, LocationUpdatesBroadcastReceiver::class.java)
            intent.action = LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES
            return PendingIntent.getBroadcast(mcontext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_task, container, false)
        puncIn = rootView!!.findViewById(R.id.btnPunchIn)
        punchOut = rootView!!.findViewById(R.id.btnPunchOut)
        spinnerShift = rootView!!.findViewById(R.id.shift_dropdown)
        puchInOutView = rootView!!.findViewById(R.id.puchInOutLayout)

        swipeTaskLayout = rootView!!.findViewById(R.id.swipeContainer_task)
        swipeTaskLayout!!.setOnRefreshListener(this)
        swipeTaskLayout!!.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        // swipeTaskLayout!!.isRefreshing = true


        recyclerView = rootView!!.findViewById(R.id.recycler_punch)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemAnim = DefaultItemAnimator()
        itemAnim.addDuration = 1000
        itemAnim.removeDuration = 1000
        recyclerView.itemAnimator = itemAnim

        puncIn?.setOnClickListener(this)
        punchOut?.setOnClickListener(this)

        val preferences = context?.getSharedPreferences("loggedIn", Context.MODE_PRIVATE)
        empId = preferences!!.getString("emplyoee_id", "0f0f0f")
        preference = MyPreference(context)
        if (preference?.punchState!!) {
            punchOut?.visibility = View.VISIBLE
            spinnerShift?.isEnabled = false
            if (puncIn?.visibility == View.VISIBLE) {
                puncIn?.visibility = View.GONE
            }
        }


        return rootView
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (spinnerShift != null) {
                showPunchDetails()
            }
        }


    }

    override fun onRefresh() {
        swipeTaskLayout?.isRefreshing = true
        loadShifts()

        Handler().postDelayed(Runnable {
            if (spinnerShift != null) {
                showPunchDetails()
            }
        }, 1000)
    }

    private fun btnPunchInAnim() {
        val myAnim = AnimationUtils.loadAnimation(context, R.anim.bounch_button_on)
        val interpolator1 = MyBounchInterpolator(0.2, 30.0)
        myAnim.interpolator = interpolator1
        puncIn!!.startAnimation(myAnim)
    }

    private fun btnPunchOutAnim() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.bounch_button_on)
        val interpolator2 = MyBounchInterpolator(0.2, 30.0)
        anim.interpolator = interpolator2
        punchOut!!.startAnimation(anim)
    }


    private fun loadShifts() {
        val service = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
        val call = service.allShifts

        call.enqueue(object : Callback<ShiftModel> {
            override fun onResponse(call: Call<ShiftModel>, response: Response<ShiftModel>) {
                info(this@TaskFragment, "$response")

                if (response.isSuccessful && response.code() == 200) {
                    val dataList = response.body()
                    if (dataList != null && dataList.data.shifts.size > 0) {
                        shifList.clear()
                        for (i in 0 until dataList.data.shifts.size) {
                            shifList.add(Shift(dataList.data.shifts[i].name, dataList.data.shifts[i].id))
                        }
                    }

                    val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, shifList)
                    spinnerShift?.adapter = adapter
                    if (preference?.selectedPosition != -1) {
                        spinnerShift?.setSelection(preference!!.selectedPosition)
                    }

                    spinnerShift!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            spinnerShift?.setSelection(position)
                            val data = parent.selectedItem as Shift
                            //displayUserData(data)

//                                val f =  DateTimeFormatterBuilder()
//                                        .parseCaseInsensitive()
//                                        .append(DateTimeFormatter.ofPattern("HH:mm")).toFormatter(Locale.ENGLISH);
//
//                                //val f = DateTimeFormatter.ofPattern( "hh:mm" );
//                                val m_start = LocalDate.parse( "08:00" , f);
//                                val m_end = LocalDate.parse( "13:00" , f);
//                                val e_start = LocalDate.parse( "14:00" , f);
//                                val e_end = LocalDate.parse( "18:00" , f);
//
//                                var currentDateTime = LocalDateTime.now()
//                                var time = currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm"))
//
//                                val todaysTime = LocalDate.parse(time, f)
//                                if (m_start <= todaysTime && todaysTime <= m_end) { //date is between date1 and date2 (both inclusive)
//                                    puchInOutView!!.isEnabled = true
//                                }else{
//                                    puchInOutView!!.isEnabled = false
//                                }
//
//
//                                if (e_start <= todaysTime && todaysTime <= e_end) { //date is between date1 and date2 (both inclusive)
//                                    puchInOutView!!.isEnabled = true
//                                }else{
//                                    puchInOutView!!.isEnabled = false
//                                }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                } else {
                    context?.showShortToast("Something wrong!")
                }

            }

            override fun onFailure(call: Call<ShiftModel>, t: Throwable) {
                error(this@TaskFragment, t.localizedMessage)

                if (swipeTaskLayout!!.isRefreshing == true) {
                    swipeTaskLayout!!.isRefreshing = false
                }


            }
        })
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun showPunchDetails() {

        if (isLoading) {
            return
        }

        isLoading = true

        try {

            val punchReq = PunchReqModel()
            punchReq.employeeId = empId
//          punchReq.shiftId = selectedID.toInt()
            punchReq.shiftId = selectedID
            punchReq.start = date
            punchReq.end = date


            val apiservice = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
            val call = apiservice.postPunchReqRawJSON(punchReq)
            call.enqueue(object : retrofit2.Callback<PunchDetailsList> {
                override fun onResponse(call: Call<PunchDetailsList>, response: Response<PunchDetailsList>) {
                    //stopDialog();
                    //dialog.stopProgressDialog()
                    info(this@TaskFragment, "$response")
                    isLoading = false

                    if (swipeTaskLayout!!.isRefreshing == true) {
                        swipeTaskLayout!!.isRefreshing = false
                    }

                    if (response.isSuccessful && response.code() == 200) {
                        val detailsList = response.body()
                        if (detailsList != null && detailsList.data.attendances.size > 0) {
                            punchDetailsLists.clear()
                            for (i in 0 until detailsList.data.attendances.size) {
                                punchDetailsLists.add(detailsList.data.attendances[i])
                                //Log.e("====> Name", detailsList.getData().get(i).getOvertime());
                            }
                        }


                        punchDetailsAdapter = PunchDetailsAdapter(punchDetailsLists, context!!)
                        recyclerView.adapter = punchDetailsAdapter
                        punchDetailsAdapter!!.notifyDataSetChanged()


                    } else {
                        context?.showShortToast("Something wrong!")
                    }

                }

                override fun onFailure(call: Call<PunchDetailsList>, t: Throwable) {
                    //hideProgress();
                    //stopDialog();
                    // showToast("Internal server error")
                    //dialog.stopProgressDialog()
                    if (swipeTaskLayout!!.isRefreshing == true) {
                        swipeTaskLayout!!.isRefreshing = false
                    }
                    error(this@TaskFragment, t.localizedMessage)
                    isLoading = false
                }
            })

        }
        catch (e: NullPointerException) {
            error(this@TaskFragment, e.localizedMessage)
            isLoading = false
        }

    }

    private fun sendPunchInDataToServer() {
        val dialog = ProgressDialog()
        dialog.showProgressDialog(activity!!, "Please wait", false)


        try {
            val json = PunchRawModel()
            json.emplyoeeId = empId
            json.shiftId = selectedID.toInt().toLong()
//            Log.e("====>", "Punch In id --> $selectedID")
            json.inTime = dateTime
//            json.outTime = dateTime
            json.type = "manual"
            json.note = "nothing"
            json.lat = getCurrentLocation().latitude
            json.lng = getCurrentLocation().longitude


            val apiservice = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
            val call = apiservice.postPunchInRawJSON(json)
            call.enqueue(object : retrofit2.Callback<PunchInOutResponse> {
                override fun onResponse(call: Call<PunchInOutResponse>, outResponse: Response<PunchInOutResponse>) {
                    dialog.stopProgressDialog()
                    info(this@TaskFragment, "$outResponse")

                    if (outResponse.isSuccessful && outResponse.code() == 200) {
                        val data = outResponse.body()
                        if (data != null && data.status.equals("success", ignoreCase = true)) {
                            punchOut?.visibility = View.VISIBLE
                            puncIn?.visibility = View.GONE
                            spinnerShift?.isEnabled = false

                            preference?.savePunchState(true, getIndex(spinnerShift, spinnerShift?.selectedItem.toString()), selectedID)
                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();

                            requestLocationUpdates()
                            Handler().postDelayed(Runnable {
                                if (spinnerShift != null) {
                                    showPunchDetails()
                                }
                            }, 500)
                        }


                    } else {
                        context?.showShortToast("Something wrong!")
                    }
                }

                override fun onFailure(call: Call<PunchInOutResponse>, t: Throwable) {
                    //hideProgress();
                    dialog.stopProgressDialog()
                    error(this@TaskFragment, t.localizedMessage)
                }
            })
        }
        catch (e: Exception) {
            error(this@TaskFragment, e.localizedMessage)
            dialog.stopProgressDialog()
        }

    }

    private fun getIndex(spinner: Spinner?, searchString: String?): Int {
        if (searchString == null || spinner!!.count == 0) {
            return -1 // Not found
        } else {
            for (i in 0 until spinner.count) {
                if (spinner.getItemAtPosition(i).toString() == searchString) {
                    //Toast.makeText(activity, "id $i", Toast.LENGTH_LONG).show()
                    return i // Found!

                }
            }
            return -1 // Not found
        }
    }

    private fun sendPunchOutDataToServer() {
        val dialog = ProgressDialog()
        dialog.showProgressDialog(activity!!, "Please wait", false)


        try {
            val json = PunchRawModel()
            json.emplyoeeId = empId
            json.shiftId = selectedID.toInt().toLong()
//            Log.e("====>", "Punch out id --> $selectedID")
//            json.inTime = dateTime
            json.outTime = dateTime
            json.type = "manual"
            json.note = "nothing"
            json.lat = getCurrentLocation().latitude
            json.lng = getCurrentLocation().longitude


            val apiservice = RetrofitBuilder.buildRetrofit().create<ApiService>(ApiService::class.java)
            val call = apiservice.postPunchOutRawJSON(json)
            call.enqueue(object : retrofit2.Callback<PunchInOutResponse> {
                override fun onResponse(call: Call<PunchInOutResponse>, response: Response<PunchInOutResponse>) {
                    dialog.stopProgressDialog()
                    info(this@TaskFragment, "$response")
                    if (response.isSuccessful && response.code() == 200) {
                        val data = response.body()
                        if (data != null && data.status.equals("success", ignoreCase = true)) {

                            punchOut!!.visibility = View.GONE
                            puncIn!!.visibility = View.VISIBLE
                            spinnerShift!!.isEnabled = true

                            preference!!.savePunchState(false, getIndex(spinnerShift, spinnerShift!!.selectedItem.toString()), selectedID)
                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();

                            removeLocationUpdates()
                            removeNotification(context!!)
                            Handler().postDelayed(Runnable {
                                if (spinnerShift != null) {
                                    showPunchDetails()
                                }
                            }, 500)
                            //conn.close()
                        }


                    } else {
                        Toast.makeText(activity, "Something wrong", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<PunchInOutResponse>, t: Throwable) {
                    //hideProgress();
                    dialog.stopProgressDialog()
                    error(this@TaskFragment, t.localizedMessage)
                }
            })
        }
        catch (e: Exception) {
            error(this@TaskFragment, e.localizedMessage)
            dialog.stopProgressDialog()
        }

    }

    private fun displayUserData(data: Shift) {
        val name = data.name
        val id = data.id
        val userData = "Name: $name\nId: $id"
        //Toast.makeText(getContext(), userData, Toast.LENGTH_LONG).show();
    }

    fun getSelectedUser(v: View) {
        val data = spinnerShift!!.selectedItem as Shift
        displayUserData(data)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPunchIn ->
                // Check if the user revoked runtime permissions.

                if (!checkPermissions()) {
                    requestPermission()
                } else {
                    //googleApiHelper.requestLocationUpdates();

                    btnPunchInAnim()
                    sendPunchInDataToServer()
                }

            R.id.btnPunchOut -> {

                //googleApiHelper.removeLocationUpdates();
                btnPunchOutAnim()
                sendPunchOutDataToServer()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Location {
        val mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (mLocation != null) {
            return mLocation
        }

        return mLocation
    }

    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    fun requestLocationUpdates() {
        try {
            info(this@TaskFragment, "Starting location updates")
            LocationRequestHelper.setRequesting(mcontext, true)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, pendingIntent)
        }
        catch (e: SecurityException) {
            LocationRequestHelper.setRequesting(mcontext, false)
            error(this@TaskFragment, e.localizedMessage)
        }

    }

    /**
     * Handles the Remove Updates button, and requests removal of location updates.
     */
    fun removeLocationUpdates() {
        info(this@TaskFragment, "Removing location updates")
        LocationRequestHelper.setRequesting(mcontext, false)
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, pendingIntent)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mcontext = context

        buildGoogleApiClient()
    }


    override fun onStart() {
        super.onStart()
        buildGoogleApiClient()
        PreferenceManager.getDefaultSharedPreferences(mcontext).registerOnSharedPreferenceChangeListener(this)
    }


    override fun onResume() {
        super.onResume()
        checkLocationServices()
        loadShifts()
//        if (!checkPermissions()){
//            requestPermission()
//        }
        updateButtonsState(LocationRequestHelper.getRequesting(mcontext))
        //mLocationUpdatesResultView.setText(LocationResultHelper.getSavedLocationResult(mcontext));
    }

    override fun onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            mGoogleApiClient?.disconnect()
        }
        PreferenceManager.getDefaultSharedPreferences(mcontext).unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = UPDATE_INTERVAL
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.maxWaitTime = MAX_WAIT_TIME
    }


    private fun buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return
        }
        mGoogleApiClient = GoogleApiClient.Builder(mcontext!!)
                .addConnectionCallbacks(this)
                .enableAutoManage(activity!!, this)
                .addApi(LocationServices.API)
                .build()
        createLocationRequest()
    }

    override fun onConnected(bundle: Bundle?) {
        info(this@TaskFragment, "GoogleApiClient connected")
        //createLocationRequest()
        if (preference!!.punchState!!) {   //if user already punched in
            requestLocationUpdates()
        }
    }

    override fun onConnectionSuspended(i: Int) {
        mGoogleApiClient!!.connect()
        val text = "Connection suspended"
        error(this@TaskFragment, "$text: Error code: $i")
        showSnackbar(text)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        val text = "Exception while connecting to Google Play services"
        error(this@TaskFragment, text + ": " + connectionResult.errorMessage)
        showSnackbar(text)
    }

    /**
     * Shows a [Snackbar] using `text`.
     *
     * @param text The Snackbar text.
     */
    private fun showSnackbar(text: String) {
        val container = activity!!.findViewById<View>(R.id.activity_main)
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
        }
    }

    fun checkLocationServices() {
        // Get Location Manager and check for GPS & Network location services
        val lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Location Services Not Active")
            builder.setMessage("Please enable Location Services and GPS (High accuracy)")
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                // Show location settings when the user acknowledges the alert dialog
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context!!.startActivity(intent)
            })
            val alertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.setCancelable(false)
            alertDialog.show()
            error(this@TaskFragment, "Location Service Not Enabled")

        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                    activity!!.findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok) {
                        // Request permission
                        //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
                    }
                    .show()
        } else {
            info(this@TaskFragment, "Requesting permission")
            // previously and checked "Never ask again".
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting

                btnPunchInAnim()
                sendPunchInDataToServer()

            } else {
                // Permission denied.
                Snackbar.make(
                        activity!!.findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings) {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == LocationResultHelper.KEY_LOCATION_UPDATES_RESULT) {
            //Toast.makeText(mcontext, LocationResultHelper.getSavedLocationResult(mcontext), Toast.LENGTH_SHORT).show()
        } else if (s == LocationRequestHelper.KEY_LOCATION_UPDATES_REQUESTED) {
            updateButtonsState(LocationRequestHelper.getRequesting(mcontext))
        }
    }


    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private fun updateButtonsState(requestingLocationUpdates: Boolean) {
        if (requestingLocationUpdates) {
            //            mRequestUpdatesButton.setEnabled(false);
            //            mRemoveUpdatesButton.setEnabled(true);
            //        } else {
            //            mRequestUpdatesButton.setEnabled(true);
            //            mRemoveUpdatesButton.setEnabled(false);
            //        }
        }
    }

    companion object {

        private val TAG = TaskFragment::class.java.simpleName
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 44
        private val UPDATE_INTERVAL = (3 * 50 * 1000).toLong()  //3 minutes
        private val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2 //1.5 minutes
        private val MAX_WAIT_TIME = UPDATE_INTERVAL * 2  // 4.5 minutes

        fun removeNotification(context: Context) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        fun newInstance(instance: Int): TaskFragment {
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = TaskFragment()
            fragment.arguments = args
            return fragment

        }
    }
}


