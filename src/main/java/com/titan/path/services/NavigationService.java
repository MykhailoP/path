package com.titan.path.services;

import com.google.gson.Gson;
import com.titan.path.algorithms.astar.Graph;
import com.titan.path.algorithms.astar.RouteFinder;
import com.titan.path.algorithms.astar.IScorer;
import com.titan.path.core.Country;
import com.titan.path.countries.ICountySourceConnection;
import com.titan.path.params.CountriesDto;
import com.titan.path.params.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toSet;

@Service
public class NavigationService implements INavigationService {

    private final ICountySourceConnection countySourceConnection;
    private final IScorer<Country> haversineScorer;

    private final Gson gson;

    private Graph<Country> graph;

    private RouteFinder<Country> countryRouteFinder;

    @Autowired
    public NavigationService(ICountySourceConnection countySourceConnection, IScorer<Country> haversineScorer, Gson gson) {
        this.countySourceConnection = countySourceConnection;
        this.haversineScorer = haversineScorer;
        this.gson = gson;
    }

    @Override
    public ResponseDto findPath(String origin, String destination) throws IOException {
        if(graph == null) {
            countySourceConnection.rawCountriesGetSync(this);
        }
        Country startNode = graph.getNode(origin);
        Country endNode = graph.getNode(destination);
        List<String> route = countryRouteFinder.findRoute(startNode, endNode).stream().map(Country::getId).collect(toList());
        return new ResponseDto(route);
    }

    @Override
    @PostConstruct
    public void preloadRawCountryData() {
        countySourceConnection.rawCountriesGetAsync(this);
    }

    @Override
    public INavigationService setRawCountry(String rawCountry) {
        this.graph = mapRawStringToGraph(rawCountry);
        if (countryRouteFinder == null) {
            countryRouteFinder = new RouteFinder<>(graph, haversineScorer, haversineScorer);
        }
        return this;
    }


    private Graph<Country> mapRawStringToGraph(String rawCountries) {
        CountriesDto[] rawDtos = gson.fromJson(rawCountries, CountriesDto[].class);
        Set<Country> countrySet = new HashSet<>(rawDtos.length);
        Map<String, Set<String>> borders = new HashMap<>(rawDtos.length);
        for (CountriesDto rawDto : rawDtos) {
            countrySet.add(rawDto.mapCountry());
            borders.put(rawDto.getCca3(), rawDto.getSetBorders());
        }
        return new Graph<>(countrySet, borders);
    }

}
