package com.titan.path;

import com.google.gson.Gson;
import com.titan.path.algorithms.astar.HaversineScorer;
import com.titan.path.countries.CountrySourceConnection;
import com.titan.path.params.ResponseDto;
import com.titan.path.services.INavigationService;
import com.titan.path.services.NavigationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@SpringBootTest
class PathApplicationTests {

    @Test
    void contextLoads() throws IOException {
        INavigationService navigationService = new NavigationService(new CountrySourceConnection(), new HaversineScorer(), new Gson());
        ResponseDto responseDtoActual = navigationService.findPath("CZE", "ESP");
        System.out.println(responseDtoActual);
    }

    @Test
    void contextCzechSpain() throws IOException {
        INavigationService navigationService = new NavigationService(new CountrySourceConnection(), new HaversineScorer(), new Gson());
        ResponseDto responseDtoActual = navigationService.findPath("CZE", "ESP");
        ResponseDto responseDtoExpected = new ResponseDto(List.of("CZE","AUT","CHE","FRA","ESP"));
        assertReflectionEquals(responseDtoExpected, responseDtoActual);
    }

    public static Object[][] spainChinaDataProvider() {
        return new Object[][]{
                {"ESP", "CHN", List.of("ESP","FRA","DEU","POL","RUS","CHN")},
                {"ESP", "UKR", List.of("ESP", "FRA", "CHE", "AUT", "SVK", "UKR")}
        };
    }

    @ParameterizedTest
    @MethodSource("spainChinaDataProvider")
    void contextSpainChina(String from, String to, List<String> expected) throws IOException {
        INavigationService navigationService = new NavigationService(new CountrySourceConnection(), new HaversineScorer(), new Gson());
        ResponseDto responseDtoActual = navigationService.findPath(from, to);
        ResponseDto responseDtoExpected = new ResponseDto(expected);
        assertReflectionEquals(responseDtoExpected, responseDtoActual);
    }

}
