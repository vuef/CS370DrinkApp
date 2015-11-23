package services;

import Define.AppDefines;
import providers.IRecipeProvider;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class APIClient {
    private static IRecipeProvider recipeProvider;

    public static IRecipeProvider getRecipeProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDefines.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        recipeProvider = retrofit.create(IRecipeProvider.class);

        return recipeProvider;
    }
}
