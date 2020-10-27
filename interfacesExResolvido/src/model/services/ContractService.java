package model.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ContractService() {
	}

	public ContractService(OnlinePaymentService onlinePaymentService) {
		super();
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(contract.getDate());
		int n = 1;
		Double amount = contract.getTotalValue();
		
		while(n<=months) {
			
			Double p1 = amount/months + (onlinePaymentService.interest(amount, months)*n); 
			Double p2 = onlinePaymentService.paymentFee(p1);
			cal.add(Calendar.MONTH, 1);
			Date dueDate = cal.getTime();
			contract.getInstallments().add(new Installment(dueDate, p2));
			n++;
		}
		
		for (Installment inst : contract.getInstallments()) {
			System.out.println(sdf.format(inst.getDueDate()) + " - " + String.format("$%.2f", inst.getAmount()));
		}
	}
}
