package com.example.demo.model;

import jakarta.persistence.Column;

public class PaymentInformation {


    @Column(name = "cardHolder_name")
    private  String cardHolderName;

    @Column(name = "cardNumber_name")
    private  String cardNumber;

    @Column(name = "expiration_time")
    private  String expirationTime;

    @Column(name = "cvv")
    private  String cvv;






}
