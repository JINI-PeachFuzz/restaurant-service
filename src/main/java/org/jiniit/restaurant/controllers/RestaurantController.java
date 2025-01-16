package org.jiniit.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.jiniit.restaurant.entities.Restaurant;
import org.jiniit.restaurant.exceptions.RestaurantNotFoundException;
import org.jiniit.restaurant.services.RestaurantInfoService;
import org.jiniit.restaurant.services.RestaurantSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantInfoService infoService;
    private final RestaurantSearchService searchService;

    @GetMapping("/info/{seq}")
    public Restaurant info(@PathVariable("seq") Long seq) {
        return infoService.get(seq);
    }

    @GetMapping("/list")
    public List<Restaurant> list(RestaurantSearch search) {
        return infoService.getList(search);
    }

    @GetMapping("/category")
    public List<String> categories() {
        return infoService.getCategories();
    }

    /**
     * 좌표로 근처에 있는 식당 조회
     *
     * @return
     */
    @GetMapping("/search")
    public List<Restaurant> search(@ModelAttribute NeighborSearch search) {
        List<Long> seq = searchService.search(search);
        if (seq == null || seq.isEmpty()) {
            return List.of();
        }

        RestaurantSearch rSearch = new RestaurantSearch();
        rSearch.setSeq(seq);
        return infoService.getList(rSearch);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Void> errorHandler() {

        return ResponseEntity.notFound().build();
    }
}
