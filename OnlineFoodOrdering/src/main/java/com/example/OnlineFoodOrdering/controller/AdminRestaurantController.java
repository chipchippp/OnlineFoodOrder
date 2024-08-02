    package com.example.OnlineFoodOrdering.controller;

    import com.example.OnlineFoodOrdering.model.Restaurant;
    import com.example.OnlineFoodOrdering.model.UserEntity;
    import com.example.OnlineFoodOrdering.request.RestaurantRequest;
    import com.example.OnlineFoodOrdering.response.MessageResponse;
    import com.example.OnlineFoodOrdering.service.impl.RestaurantService;
    import com.example.OnlineFoodOrdering.service.impl.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("api/v1/admin/restaurant")
    public class AdminRestaurantController {
        private final RestaurantService restaurantService;
        private final UserService userService;

        @Autowired
        public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
            this.restaurantService = restaurantService;
            this.userService = userService;
        }

        @PostMapping
        public ResponseEntity<Restaurant> saveRestaurant(
                @RequestBody RestaurantRequest res,
                @RequestHeader("Authorization") String jwt
        ) throws Exception {

            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.saveRestaurant(res, user);
            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Restaurant> updateRestaurant(
                @RequestBody RestaurantRequest res,
                @RequestHeader("Authorization") String jwt,
                @PathVariable Long id
        ) throws Exception {

            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.updateRestaurant(id, res);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<MessageResponse> deleteRestaurant(
                @RequestHeader("Authorization") String jwt,
                @PathVariable Long id
        ) throws Exception {

            UserEntity user = userService.findByUserByJwtToken(jwt);
            restaurantService.deleteRestaurant(id);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Restaurant deleted successfully");
            return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
        }

        @PutMapping("/{id}/status")
        public ResponseEntity<Restaurant> updateRestaurantStatus(
                @RequestHeader("Authorization") String jwt,
                @PathVariable Long id
        ) throws Exception {

            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }

        @GetMapping("/user")
        public ResponseEntity<Restaurant> findRestaurantById(
                @RequestHeader("Authorization") String jwt
        ) throws Exception {

            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }

    }
