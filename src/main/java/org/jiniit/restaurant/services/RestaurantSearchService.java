package org.jiniit.restaurant.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jiniit.restaurant.controllers.NeighborSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class RestaurantSearchService {
    @Value("${python.path}")
    private String runPath;

    @Value("${python.script}")
    private String scriptPath;

    @Autowired
    private ObjectMapper om;

    public List<Long> search(NeighborSearch search) {
        double lat = search.getLat();
        double lon = search.getLon();
        int limit = search.getLimit();

        try {

            String data = om.writeValueAsString(List.of(limit, lat, lon));

            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "search.py", data);
            Process process = builder.start();
            InputStream in = process.getInputStream();
            return om.readValue(in.readAllBytes(), new TypeReference<>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }
}
