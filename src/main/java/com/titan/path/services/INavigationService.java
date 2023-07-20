package com.titan.path.services;

import com.titan.path.params.ResponseDto;

import java.io.IOException;

public interface INavigationService {

    ResponseDto findPath(String origin, String destination) throws IOException;

    void preloadRawCountryData();

    INavigationService setRawCountry(String rawCountry);

}
