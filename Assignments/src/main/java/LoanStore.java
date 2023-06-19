import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanStore {
    List<Loan> loanList = new ArrayList<>();
    Map<String, Double> lenderAggregationWithRemainingAmountAndIP;
    Map<String, Double> interestAggregationWithRemainingAmountAndIP;
    Map<String, Double> customerIdAggregationWithRemainingAmountAndIP;

    public LoanStore() {
        lenderAggregationWithRemainingAmountAndIP = new HashMap<>();
        interestAggregationWithRemainingAmountAndIP = new HashMap<>();
        customerIdAggregationWithRemainingAmountAndIP = new HashMap<>();
    }
    public Boolean checkLoanValidity(LocalDate paymentDate, LocalDate dueDate) throws Exception {
        if(paymentDate.isAfter(dueDate)){
            return true;
        }else{
            return false;
        }
    }

    public void checkDueDate(LocalDate dueDate){
        if(LocalDate.now().isAfter(dueDate)){
            System.out.println("Due date for this loan has been crossed");
        }
    }

    public void addLoan(Loan loan) throws Exception {

        if(checkLoanValidity(loan.getPaymentDate(), loan.getDueDate())){
            throw new Exception("This loan is not valid since the payment date is greater than due date");
        }
        loanList.add(loan);
        doAggregation(loan);
        checkDueDate(loan.getDueDate());
    }

    public void doAggregation(Loan loan){
        lenderAggregationWithRemainingAmountAndIP.put(loan.getLenderId(), loan.getRemainingAmount()+loan.getInterestPerDay());
        interestAggregationWithRemainingAmountAndIP.put(loan.getLenderId(), loan.getRemainingAmount()+loan.getInterestPerDay());
        customerIdAggregationWithRemainingAmountAndIP.put(loan.getLenderId(), loan.getRemainingAmount()+loan.getInterestPerDay());

    }
    public static void main(String[] args) {
        LoanStore loanStore = new LoanStore();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try {
            loanStore.addLoan(new Loan("L1", "C1", "LEN1", 10000, 10000,
                    LocalDate.parse("05/06/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
            loanStore.addLoan(new Loan("L2", "C2", "LEN2", 10000, 10000,
                    LocalDate.parse("05/06/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
            loanStore.addLoan(new Loan("L3", "C3", "LEN3", 10000, 10000,
                    LocalDate.parse("05/06/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
            loanStore.addLoan(new Loan("L4", "C4", "LEN4", 10000, 10000,
                    LocalDate.parse("05/06/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
