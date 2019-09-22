package com.example.marketim.api;

public abstract class Callback<T> {
    public abstract void returnResult(T t);

    public abstract void returnError(String message);
}