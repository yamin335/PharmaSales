package com.fantasyapps.darmahealthcare.networks;


import com.fantasyapps.darmahealthcare.models.AddCustomer.raw.CustomerRawModel;
import com.fantasyapps.darmahealthcare.models.AddCustomer.sucessResponse.SuccessResponseModel;
import com.fantasyapps.darmahealthcare.models.PuchInOut.PunchInOutResponse;
import com.fantasyapps.darmahealthcare.models.PuchInOut.PunchRawModel;
import com.fantasyapps.darmahealthcare.models.PunchDetails.PunchDetailsList;
import com.fantasyapps.darmahealthcare.models.PunchReqRaw.PunchReqModel;
import com.fantasyapps.darmahealthcare.models.SalesModule.SalesModelList;
import com.fantasyapps.darmahealthcare.models.SalesModule.rawJson.RawSalesModel;
import com.fantasyapps.darmahealthcare.models.SubmitOrder.OrderSubmitResponse;
import com.fantasyapps.darmahealthcare.models.SubmitOrder.raw.SendOrderModel;
import com.fantasyapps.darmahealthcare.models.customer.CustomerModel;
import com.fantasyapps.darmahealthcare.models.customerType.CustomerTypeModel;
import com.fantasyapps.darmahealthcare.models.login.LoginResponseModel;
import com.fantasyapps.darmahealthcare.models.product.AddProductModelList;
import com.fantasyapps.darmahealthcare.models.shift.ShiftModel;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("hrmLaravel/api/login")
    Call<LoginResponseModel> userLogin(@Field("email") String email, @Field("password") String password
    );

    @GET("erpLaravel/api/customer")
    Call <CustomerModel> getAllCustomer();

    @GET("erpLaravel/api/product")
    Call <AddProductModelList> getAllProducts();


    @GET("erpLaravel/api/sale")
    Call <SalesModelList> getAllSales();


//    @GET("erpLaravel/api/sales/filter/salesman_id/{id}")
//    Call <SalesModelList> getSalesData(@Path("id") int salesManId, @Query("index") int pageIndex);    //erpLaravel/api/sales/filter/salesman_id/1?page=1

    @Headers("Content-Type: application/json")
    @POST("erpLaravel/api/sales/filter")
    Call<SalesModelList> getSalesData(@Body RawSalesModel body);

    @Headers("Content-Type: application/json")
    @POST("erpLaravel/api/sale")
    Call<OrderSubmitResponse> postOrderRawJSON(@Body SendOrderModel body);

    @GET("erpLaravel/api/customer-type")
    Call <CustomerTypeModel> getAllTypes();

    @Headers("Content-Type: application/json")
    @POST("erpLaravel/api/customer")
    Call<SuccessResponseModel> postCustomerRawJSON(@Body CustomerRawModel body);

    @GET("hrmLaravel/api/shifts")
    Call <ShiftModel> getAllShifts();


    @Headers("Content-Type: application/json")
    @POST("hrmLaravel/api/attendances")
    Call<ResponseBody> postPunchRawJSON(@Body PunchRawModel body);

    @Headers("Content-Type: application/json")
    @POST("hrmLaravel/api/punch-in")
    Call<PunchInOutResponse> postPunchInRawJSON(@Body PunchRawModel body);


    @Headers("Content-Type: application/json")
    @POST("hrmLaravel/api/punch-out")
    Call<PunchInOutResponse> postPunchOutRawJSON(@Body PunchRawModel body);

    @Headers("Content-Type: application/json")
    @POST("hrmLaravel/api/attendance/search")
    Call<PunchDetailsList> postPunchReqRawJSON(@Body PunchReqModel body);


}
