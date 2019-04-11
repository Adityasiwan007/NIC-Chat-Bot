package tcs.ril.storebot.Network;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tcs.ril.storebot.model.AuthToken;
import tcs.ril.storebot.model.PickTasks;
import tcs.ril.storebot.model.Response;
import tcs.ril.storebot.model.SendQuery;
import tcs.ril.storebot.model.UserCredential;


public interface UserServices {


    @POST("spotlight/user/auth")
    Call<AuthToken> login(@Body UserCredential userCredentials);

    @POST("spotlight/associate/auth")
    Call<AuthToken> loginAssociate(@Body UserCredential userCredential);

    @POST("spotlight/associate/assistance")
    Call<Response> chatResponse(@Query("token") String token, @Body SendQuery sendQueryObj);

    @GET("spotlight/associate/user/picktasks")
    Call<PickTasks> profileResponse(@Query("token") String token);


}