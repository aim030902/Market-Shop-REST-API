package uz.aim.marketshop.exceptions;

public class GenericNotFoundException extends GenericRuntimeException {
    public GenericNotFoundException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
