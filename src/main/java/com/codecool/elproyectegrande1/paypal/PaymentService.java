package com.codecool.elproyectegrande1.paypal;

import java.net.URI;

public interface PaymentService {

    CreatedOrder createOrder(Double totalAmount, URI returnUrl);

    void captureOrder(String orderId);
}
