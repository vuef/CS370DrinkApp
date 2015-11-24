package services;

import Define.AppDefines;
import providers.IDrinkProvider;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class APIClient {
    private static IDrinkProvider drinkProvider;

    public static IDrinkProvider getDrinkProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDefines.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        drinkProvider = retrofit.create(IDrinkProvider.class);

        return drinkProvider;
    }
}
