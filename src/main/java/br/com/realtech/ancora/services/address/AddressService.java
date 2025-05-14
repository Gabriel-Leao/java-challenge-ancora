package br.com.realtech.ancora.services.address;

import br.com.realtech.ancora.dtos.address.request.UpsertAddressRequest;
import br.com.realtech.ancora.dtos.address.response.AddressResponse;
import br.com.realtech.ancora.entities.Address;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.repositories.address.AddressRepository;
import br.com.realtech.ancora.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressResponse findByUserId(UUID userId) {
        Address address = addressRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Address not found for user"));
        return new AddressResponse(address);
    }

    public AddressResponse saveAddress(UUID userId, UpsertAddressRequest request) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Address address = user.getAddress() != null ? user.getAddress() : new Address();

        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setComplement(request.getComplement());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());

        if (user.getAddress() == null) {
            address.setUser(user);
            user.setAddress(address);
            addressRepository.save(address);
            userRepository.save(user);
        }

        return new AddressResponse(address);
    }

    public AddressResponse updateAddress(UUID userId, UpsertAddressRequest request) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Address address = user.getAddress();

        if (address == null) {
            address = new Address();
            address.setUser(user);
            user.setAddress(address);
        }

        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setComplement(request.getComplement());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());

        return new AddressResponse(addressRepository.save(address));
    }

    @Transactional
    public void deleteByUserId(UUID userId) {
        addressRepository.deleteByUserId(userId);
    }
}
