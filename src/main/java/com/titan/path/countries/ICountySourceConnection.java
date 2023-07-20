package com.titan.path.countries;

import com.titan.path.services.INavigationService;

import java.io.IOException;

public interface ICountySourceConnection {

    void rawCountriesGetAsync(INavigationService navigationService);
    void rawCountriesGetSync(INavigationService navigationService) throws IOException;

}
