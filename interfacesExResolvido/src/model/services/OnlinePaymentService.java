package model.services;

public interface OnlinePaymentService {
	
	public Double paymentFee(Double amount);
	
	Double interest(Double amount, Integer months);
}
