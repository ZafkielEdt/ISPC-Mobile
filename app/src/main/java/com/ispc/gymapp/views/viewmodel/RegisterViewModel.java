package com.ispc.gymapp.views.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<HashMap<String, Object>> inputDataMap  = new MutableLiveData<>();
    private HashMap<String, Object> data = getInputData().getValue();

    public void setInputData(String key,Object value) {
        if(data==null){
            data = new HashMap<>();
        }

        data.put(key,value);
        inputDataMap.setValue(data);

    }

    public LiveData<HashMap<String, Object>> getInputData() {
        return inputDataMap ;
    }
}