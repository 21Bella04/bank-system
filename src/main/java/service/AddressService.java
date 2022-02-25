package service;

import entity.Address;
import lombok.AllArgsConstructor;
import repository.AddressRepository;

@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddress(int id) {
        return addressRepository.getAddress(id);
    }

    public void saveAddress(Address address) {
        addressRepository.saveAddress(address);
    }

    public void deleteAddress(Integer accountId) {
        addressRepository.deleteAddress(accountId);
    }



}
