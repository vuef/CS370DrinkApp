package mycrosoft.drinkapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.APIClient;
import models.DOTDModel;

//Created by MAtt on 11/14/2015.

public class DrinkOfTheDay extends Fragment{
    private ImageView drinkImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_dotd, container, false);
        drinkImage = (ImageView)view.findViewById(R.id.drinkOfTheDay);


        APIClient.getRecipeProvider()
                .getDOTDRecipeById("14071")//drinkItem.drinkId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DOTDModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 0;
                    }

                    @Override
                    public void onNext(DOTDModel drinkModel) {
                        // Handle the results
                        if (drinkModel != null) {
                            Picasso.with(getActivity().getBaseContext()).load(drinkModel.thumbnail).placeholder(R.drawable.icon).into(drinkImage);
                            // Create an inline adapter instead of using an inheriting class


                        } else {
                            // handle null
                        }
                    }
                });

        return  view;
    }

}
