package com.ispc.gymapp.views.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> inputData = new MutableLiveData<>();


    public void setInputData(String value){
        inputData.setValue(value);
    }

    public LiveData<String> getInputData(){
        return inputData;
    }
}
