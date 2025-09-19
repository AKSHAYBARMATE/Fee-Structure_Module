package com.schoolerp.feemodule.service;


import com.schoolerp.feemodule.entity.FeePayment;
import com.schoolerp.feemodule.request.FeePaymentRequest;
import com.schoolerp.feemodule.response.FeePaymentResponse;

public interface FeePaymentService {
    FeePaymentResponse collectFee(FeePaymentRequest request);
}

