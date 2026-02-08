package org.example.paymentservice.exception;

public class NotNullException extends RuntimeException
{
    public NotNullException(String message)
    {
        super(message);
    }
    public NotNullException(Throwable cause)
    {
        super(cause);
    }
    public NotNullException(String message, Throwable cause)
    {
        this(cause);
    }
}
