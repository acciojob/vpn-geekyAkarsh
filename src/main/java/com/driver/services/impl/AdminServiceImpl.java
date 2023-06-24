package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {

        Admin admin = new Admin(username,password);

        Admin savedAdmin = adminRepository1.save(admin);
        return savedAdmin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {

        ServiceProvider serviceProvider = new ServiceProvider(providerName);
        Admin admin = adminRepository1.findById(adminId).get();

        admin.getServiceProviders().add(serviceProvider);
        serviceProvider.setAdmin(admin);
        Admin savedAdmin = adminRepository1.save(admin);
        return savedAdmin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

        CountryName cn = CountryName.valueOf(countryName.toUpperCase());
        if(cn == null){
            throw new Exception("Country not found");
        }
        Country country = new Country(cn, countryName.toUpperCase());
        country.setUser(null);
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository1.findById(serviceProviderId);

        if(!optionalServiceProvider.isPresent()){
            throw new Exception("Service Provider Not Present");
        }

        ServiceProvider serviceProvider = optionalServiceProvider.get();

        country.setServiceProvider(serviceProvider);
        serviceProvider.getCountryList().add(country);
        ServiceProvider savedServiceProvider = serviceProviderRepository1.save(serviceProvider);

        return savedServiceProvider;
    }
}
