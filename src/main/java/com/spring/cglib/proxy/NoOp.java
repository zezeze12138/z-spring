package com.spring.cglib.proxy;

public interface NoOp extends Callback {

    NoOp INSTANCE = new NoOp() {

    };

}
