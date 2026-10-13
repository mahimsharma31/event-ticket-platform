package com.example.Ticket.Booking.App.exception;

public class TicketTypeSoldOutException extends EventTicketException {
    public TicketTypeSoldOutException() {
    }

    public TicketTypeSoldOutException(String message) {
        super(message);
    }

    public TicketTypeSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeSoldOutException(Throwable cause) {
        super(cause);
    }

    public TicketTypeSoldOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
