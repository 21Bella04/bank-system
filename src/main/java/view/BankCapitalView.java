package view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankCapitalView {
    private BigDecimal bankCapital;
    private BigDecimal averageBankCapital;

    @Override
    public String toString() {
        return  "BankCapital: " + bankCapital +
                "\nAverageBankCapital per account=" + averageBankCapital + "\n";
    }
}
