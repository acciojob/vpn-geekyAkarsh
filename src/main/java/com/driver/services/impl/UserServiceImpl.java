package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
//create a user of given country. The originalIp of the user should be "countryCode.userId" and return the user. Note that right now user is not connected and thus connected would be false and maskedIp would be null
        //Note that the userId is created automatically by the repository layer

        User user = new User(username,password,null,null,Boolean.FALSE);
        User savedUser = userRepository3.save(user);
        int userId = savedUser.getId();
        savedUser.setOriginalIp(CountryName.valueOf(countryName) + "." + userId);
        Country userCountry = new Country(CountryName.valueOf(countryName), countryName);
        user.setCountry(userCountry);

        savedUser = userRepository3.save(user);
        return savedUser;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUsers().add(user);
        User savedUser = userRepository3.save(user);

        return savedUser;
    }
}
