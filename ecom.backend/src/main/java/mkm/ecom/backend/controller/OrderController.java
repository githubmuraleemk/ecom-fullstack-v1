package mkm.ecom.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import mkm.ecom.backend.model.Order;
import mkm.ecom.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/e-com/api/v1/")
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Order> postOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.postOrder(order));
    }

    @GetMapping("/order/all")
    public ResponseEntity <List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        if(order==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id){
        Order updatedOrder = orderService.updateOrder(order,id);
        if(updatedOrder==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity <Map<String,String>> deleteOrder(@PathVariable Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity
                    .ok(Collections.singletonMap("message", "Order with ID " + id + " deleted successfully"));

        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));

        }
    }
}
