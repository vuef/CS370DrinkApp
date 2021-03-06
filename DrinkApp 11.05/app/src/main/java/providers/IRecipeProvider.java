package providers;

import models.DrinkModel;
import models.DrinkSearchResultModel;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface IRecipeProvider {
    @GET("/api/json/v1/1/search.php")
    Observable<DrinkSearchResultModel> getRecipesByIngredient(@Query("s") String ingredient);

    @GET("/api/json/v1/1/lookup.php")
    Observable<DrinkModel> getRecipeById(@Query("i") String recipeId);

}
