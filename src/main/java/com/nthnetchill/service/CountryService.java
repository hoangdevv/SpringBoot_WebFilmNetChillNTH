package com.nthnetchill.service;

import com.nthnetchill.model.Country;
import com.nthnetchill.model.Director;
import com.nthnetchill.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    @Transactional
    public Country getOneCountry(Long countryId) {
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        if (countryOptional.isPresent()) {
            return countryOptional.get();
        } else {
            throw new RuntimeException("Actor not found with id " + countryId);
        }
    }
    @Transactional
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    public Country updateCountry(Long countryId, Country updatedCountry) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id " + countryId));
        country.setName(updatedCountry.getName());
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id " + countryId));
        countryRepository.delete(country);
    }
}
