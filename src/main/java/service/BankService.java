package service;

import lombok.AllArgsConstructor;
import repository.ViewRepository;
import view.BankCapitalView;

@AllArgsConstructor
public class BankService {
    private final ViewRepository viewRepository;

    public BankCapitalView getBankCapitalInfo() {
        return viewRepository.getBankCapitalInfo();
    }

}
