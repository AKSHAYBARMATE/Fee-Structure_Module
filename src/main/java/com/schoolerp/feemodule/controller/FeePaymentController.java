package com.schoolerp.feemodule.controller;


import com.schoolerp.feemodule.entity.FeePayment;
import com.schoolerp.feemodule.request.FeePaymentRequest;
import com.schoolerp.feemodule.response.FeePaymentResponse;
import com.schoolerp.feemodule.response.StandardResponse;
import com.schoolerp.feemodule.service.FeePaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms/api/v1/fee-structures-module")
public class FeePaymentController {

    @Autowired
    private FeePaymentService feePaymentService;

    // Endpoint to collect fee payment
    @PostMapping("/collect")
    public ResponseEntity<StandardResponse<FeePaymentResponse>> collectFee(@RequestBody FeePaymentRequest request) {
        try {
            // Process the fee payment
            FeePaymentResponse feePayment = feePaymentService.collectFee(request);

            // Create a response object with success status
            StandardResponse<FeePaymentResponse> response = StandardResponse.<FeePaymentResponse>builder()
                    .success(true)
                    .message("Fee collected successfully")
                    .data(feePayment)
                    .timestamp(java.time.LocalDateTime.now())
                    .build();

            // Return response with HTTP status OK
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Handle error, could be anything from validation to service layer failure
            StandardResponse<FeePaymentResponse> errorResponse = StandardResponse.<FeePaymentResponse>builder()
                    .success(false)
                    .message("Error during fee collection: " + e.getMessage())
                    .data(null)
                    .timestamp(java.time.LocalDateTime.now())
                    .build();

            // Return response with HTTP status internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

