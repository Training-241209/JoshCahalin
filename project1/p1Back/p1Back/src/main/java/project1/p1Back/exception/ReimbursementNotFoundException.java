package project1.p1Back.exception;

public class ReimbursementNotFoundException extends RuntimeException{
    public ReimbursementNotFoundException(String message) {
        super(message);
}
}