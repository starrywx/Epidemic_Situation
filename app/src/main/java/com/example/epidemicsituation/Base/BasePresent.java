package com.example.epidemicsituation.Base;

public interface BasePresent<T>  {

   void attachView(T view);

   void detachView();
}
