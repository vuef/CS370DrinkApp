package providers;

import models.DrinkSearchResultModel;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface IDrinkProvider {
    @GET("/api/json/v1/1/search.php")
    Observable<DrinkSearchResultModel> getDrinksByDrinkName(@Query("s") String name);

    @GET("/api/json/v1/1/filter.php")
    Observable<DrinkSearchResultModel> getDrinkByIngred(@Query("i") String ingredient);

    @GET("/api/json/v1/1/lookup.php")
    Observable<DrinkSearchResultModel> getRandomRecipeById(@Query("i") String recipeId);

    @GET("/api/json/v1/1/lookup.php")
    Observable<DrinkSearchResultModel> getDOTDRecipeById(@Query("i") String recipeId);


}
