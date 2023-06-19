import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanStoreTest {
    private LoanStore loanStore;
    private DateTimeFormatter dateTimeFormatter;

    @Before
    public void setup() {
        loanStore = new LoanStore();
        dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }

    @Test
    public void testAddValidLoan() throws Exception {
        loanStore.addLoan(new Loan("L1", "C1", "LEN1", 10000, 10000,
                LocalDate.parse("05/06/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
        Assert.assertEquals(1, loanStore.loanList.size());
    }

    @Test(expected = Exception.class)
    public void testAddInvalidLoan() throws Exception {
        loanStore.addLoan(new Loan("L1", "C1", "LEN1", 10000, 10000,
                LocalDate.parse("05/08/2023", dateTimeFormatter), 1, LocalDate.parse("05/07/2023", dateTimeFormatter), 0));
    }

    @Test
    public void testCheckLoanValidity() throws Exception {
        boolean valid = loanStore.checkLoanValidity(LocalDate.parse("05/06/2023", dateTimeFormatter),
                LocalDate.parse("05/07/2023", dateTimeFormatter));
        Assert.assertFalse(valid);

        valid = loanStore.checkLoanValidity(LocalDate.parse("05/08/2023", dateTimeFormatter),
                LocalDate.parse("05/07/2023", dateTimeFormatter));
        Assert.assertTrue(valid);
    }

    @Test
    public void testCheckDueDate() {
        loanStore.checkDueDate(LocalDate.now().minusDays(1));
        // The due date is not crossed, so no output is expected.

        loanStore.checkDueDate(LocalDate.now().minusDays(2));
        // The due date is crossed, so "Due date for this loan has been crossed" should be printed.
    }


}