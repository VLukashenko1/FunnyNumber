package com.vitaliy.funnynumber;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vitaliy.funnynumber.Room.History;
import com.vitaliy.funnynumber.Util.FactDownloaderNetworkUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    public static Integer TOAST_REQUEST_CODE = 1;
    public static Integer ALERT_REQUEST_CODE = 2;

    private final MutableLiveData<HashMap<Integer, String>> mutableLiveDataFact = new MutableLiveData<>();
    public LiveData<HashMap<Integer, String>> liveDataFact = mutableLiveDataFact;

    private final MutableLiveData<List<History>> mutableLiveDataHistory = new MutableLiveData<>();
    public LiveData<List<History>> liveDataHistory = mutableLiveDataHistory;

    public void loadData() {
        final List<History> history  = App.getInstance().getHistoryDAO().getAll();
        mutableLiveDataHistory.setValue(history);
    }

    public void getRandFact() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        if (!App.isOpenNetwork()) {
            hashMap.put(TOAST_REQUEST_CODE, "no internet connection");
            mutableLiveDataFact.setValue(hashMap);
        }
        Flowable.fromCallable(() -> FactDownloaderNetworkUtil.getInstance().getRandFact())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s == null || s.isEmpty()) return;
                    hashMap.put(ALERT_REQUEST_CODE, s);
                    mutableLiveDataFact.setValue(hashMap);
                });

    }

    public void getFact(EditText numberInput) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        if (numberInput == null || numberInput.getText() == null || numberInput.getText().toString().isEmpty()) {
            hashMap.put(TOAST_REQUEST_CODE, "Enter a number to get a fact about it");
            mutableLiveDataFact.setValue(hashMap);
            return;
        }
        if (!App.isOpenNetwork()) {
            hashMap.put(TOAST_REQUEST_CODE, "no internet connection");
            mutableLiveDataFact.setValue(hashMap);
            return;
        }

        int number = Integer.parseInt(numberInput.getText().toString());

        Flowable.fromCallable(() -> FactDownloaderNetworkUtil.getInstance().getFact(number))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s == null || s.isEmpty()) return;
                    hashMap.put(ALERT_REQUEST_CODE, s);
                    mutableLiveDataFact.setValue(hashMap);
                });
    }

    public void clearDataFact() {
        mutableLiveDataFact.setValue(new HashMap<>());
    }
}
