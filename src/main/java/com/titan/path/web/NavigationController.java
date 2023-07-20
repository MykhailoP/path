package com.titan.path.web;

import com.titan.path.params.ResponseDto;
import com.titan.path.services.INavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class NavigationController {

    private final INavigationService navigationService;

    @Autowired
    public NavigationController(INavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @GetMapping("/routing/{origin}/{destination}")
    public ResponseDto routing(@PathVariable String origin, @PathVariable String destination) throws IOException {
        return navigationService.findPath(origin, destination);
    }

}
