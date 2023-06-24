package com.driver.services.impl;

import com.driver.model.*;
import com.driver.model.User;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{

        User user = userRepository2.findById(userId).get();

        if(user.getConnected()){
            throw new Exception("Already connected");
        }

        String country = user.getOriginalCountry().toString();

        if(countryName.equals(country)) return user;

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        Integer id = Integer.MAX_VALUE;
        for(ServiceProvider serviceProvider : serviceProviderList){

            if(serviceProvider.getCountryList().contains(countryName)){
                id = Math.min(id,serviceProvider.getId());
            }
        }

        if(id == Integer.MAX_VALUE){
            throw new Exception("Unable to connect");
        }

        user.setMaskedIp(CountryName.valueOf(countryName) + "." + id + "." + userId);
        user.setConnected(Boolean.TRUE);
        User savedUser = userRepository2.save(user);

        return savedUser;
    }
    @Override
    public User disconnect(int userId) throws Exception {

        User user = userRepository2.findById(userId).get();
        if(user.getConnected() == Boolean.FALSE){
            throw new Exception("Already disconnected");
        }

        user.setMaskedIp(null);
        user.setConnected(Boolean.FALSE);

        User savedUser = userRepository2.save(user);

        return savedUser;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {

        return userRepository2.findById(senderId).get();
    }
}
