package tcs.ril.storebot.Network;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tcs.ril.storebot.model.PreferenceManager;
import tcs.ril.storebot.view.Activity.LoginActivity;

/**
 * Created by 772250 on 12/20/2017.
 */

public class Util {
    public static String AccessToken = " ";
    public static String empId;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(LoginActivity.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    private static Retrofit retrofit = getRetrofitInstance();

    public static UserServices getUserService() {
        return getRetrofitInstance().create(UserServices.class);
    }

    public static String getAccessToken() {
        return AccessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public static String getEmpId() {
        return empId;
    }

    public static void setEmpId(String empId) {
        Util.empId = empId;
    }

    public static <S> S createService(
            Class<S> UserServices) {
        return retrofit.create(UserServices);
    }

}
