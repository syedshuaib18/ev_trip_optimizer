package com.example.ev_roadtrip_optimizer.routing;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;

@Service
public class RoutingService {

    private final RestTemplate restTemplate;

    public RoutingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public RouteResponse calculateRoute(
            double originLatitude,
            double originLongitude,
            double destinationLatitude,
            double destinationLongitude) {

        String url = String.format(
                "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=false",
                originLongitude,
                originLatitude,
                destinationLongitude,
                destinationLatitude
        );
        Map response = restTemplate.getForObject(url,Map.class);
        List<Map<String,Object>>routes=(List<Map<String, Object>>)response.get("routes");
        Map<String ,Object>route = routes.get(0);


        double distanceKm= ((Number)route .get("distance")).doubleValue()/1000;
        double durationMinutes = ((Number)route.get("duration")).doubleValue()/60;
        return new RouteResponse(distanceKm,durationMinutes);
    }
}