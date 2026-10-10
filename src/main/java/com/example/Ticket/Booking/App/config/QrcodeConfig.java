package com.example.Ticket.Booking.App.config;

import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrcodeConfig {

    @Bean
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter();
    }
}
