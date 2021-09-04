package com.fantasyapps.darmahealthcare

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.fantasyapps.darmahealthcare.models.login.LoginResponseModel
import com.fantasyapps.darmahealthcare.networks.ApiService
import com.fantasyapps.darmahealthcare.networks.RetrofitBuilder

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fantasyapps.darmahealthcare.extensions.error
import com.fantasyapps.darmahealthcare.extensions.info
import com.fantasyapps.darmahealthcare.extensions.showDebugShortToast
import com.fantasyapps.darmahealthcare.extensions.showShortToast
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.R.id.message
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser


class LoginActivity : AppCompatActivity() {


    private val isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("loggedIn", false)

    private val sharedPreferences: SharedPreferences
        get() = applicationContext.getSharedPreferences("loggedIn", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT <= 24) {
            alert("This app won't work into your device.", "Sorry!") {
                yesButton { System.exit(0)}
            }.show().setCancelable(false)
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val edtUsername = findViewById<EditText>(R.id.edt_username)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        //Button btnRegister = findViewById(R.id.btn_linkToRegisterScreen);

        if (isLoggedIn) {
            val user = sharedPreferences.getInt("user", 0)
            if (user == 1) {
                gotoMainActivity()
            } else if (user == 2) {
                //gotoUserActivity();
            }
        }

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()


            callLoginApi(username, password)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun callLoginApi(uName: String, uPass: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        val service = RetrofitBuilder.buildRetrofit().create(ApiService::class.java)
        val call = service.userLogin(uName, uPass)

        call.enqueue(object : Callback<LoginResponseModel> {
            @RequiresApi(api = Build.VERSION_CODES.O)
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) {
                progressDialog.dismiss()
                info(this, "$response")

                    if (response.isSuccessful && response.code() == 200) {
                        val dataList = response.body()
                        if (dataList != null) {
                            Log.e("===>", "datalist is not null")
                            if (dataList.status.equals("success", ignoreCase = true)) {
                                if (dataList.data != null){
                                    val id = dataList.data.user.id
                                    val employeeId = dataList.data.user.employeeId
                                    val nationalId = dataList.data.user.nationalId
                                    val firstName = dataList.data.user.firstName
                                    val lastName = dataList.data.user.lastName
                                    val displayName = dataList.data.user.displayName
                                    val email = dataList.data.user.email
                                    val password = dataList.data.user.password
                                    val phone = dataList.data.user.phone
                                    val dob = dataList.data.user.dob //date of birth
                                    val gender = dataList.data.user.gender
                                    val martialStatus = dataList.data.user.maritalStatus
                                    val workStation = dataList.data.user.workstation
                                    val drivingLicense = dataList.data.user.drivingLicense
                                    val immigrationStatus = dataList.data.user.immigrationStatus
                                    val nationality = dataList.data.user.nationality
                                    val status = dataList.data.user.status.toString()
                                    val joinedDate = dataList.data.user.joinedDate
                                    val confirmationDate = dataList.data.user.confirmationDate
                                    val emplyoee_status_id = dataList.data.user.employmentStatusId.toString()
                                    val department_id = dataList.data.user.departmentId.toString()
                                    val supervisor_id = dataList.data.user.supervisorId.toString()
                                    val offday_id = dataList.data.user.offDayId.toString()
                                    val leaveCategory_id = dataList.data.user.leavecategoryId.toString()
                                    val salarySetting_id = dataList.data.user.salarySettingId.toString()
                                    val emplyoee_type_id = dataList.data.user.employeeTypeId.toString()
                                    val emplyoee_role_id = dataList.data.user.employeeRoleId.toString()
                                    val present_address_id = dataList.data.user.presentAddressId.toString()
                                    val permanent_address_id = dataList.data.user.permanentAddressId.toString()
                                    val branch_id = dataList.data.user.branchId
                                    val created_at = dataList.data.user.createdAt
                                    val updated_at = dataList.data.user.updatedAt

                                    showShortToast("You're logged in")
                                    //saveLoginDetails(id, name, email, password, image, phone, address, serviceAreaId);
                                    setSharedPreferences(id, employeeId, "$firstName $lastName", uName, uPass, branch_id, 1)
                                    gotoMainActivity()
                                }else{
                                    showShortToast("No user data found")
                                }
                            } else if (dataList.status.equals("error", ignoreCase = true)) {
                                showShortToast("Invalid Login Details")
                            }
                        } else {
                            showShortToast("Something wrong!")
                        }
                    }

            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                progressDialog.dismiss()
                showDebugShortToast(t.localizedMessage)
                error(this@LoginActivity , t.localizedMessage)
            }
        })

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setSharedPreferences(id: Long, emp_id: String, emp_name: String, username: String, password: String, branchId: Int, user: Int) {
        val spe = sharedPreferences.edit()
        spe.putLong("id", id)
        //      spe.putLong("Zonetime", ZonedDateTime.now().toInstant().toEpochMilli());
        spe.putString("Zonetime", emp_id)
        spe.putString("emplyoee_id", emp_id)
        spe.putString("emplyoee_name", emp_name)
        spe.putInt("branchId", branchId)
        spe.putString("username", username)
        spe.putString("password", password)
        spe.putInt("user", user)
        spe.putBoolean("loggedIn", true)
        spe.commit()
    }

    private fun gotoMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
