package mkm.ecom.backend.service;

import mkm.ecom.backend.exception.ResourceNotFoundException;
import mkm.ecom.backend.model.Order;
import mkm.ecom.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Order postOrder(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with id :" + id));
    }

    public Order updateOrder(Order newOrder, Long id){
        Order existingOrder = getOrderById(id);
        existingOrder.setCustomerId(newOrder.getCustomerId());
        existingOrder.setProductId(newOrder.getProductId());
        existingOrder.setProductPrice(newOrder.getProductPrice());
        existingOrder.setQuantity(newOrder.getQuantity());
        existingOrder.setOrderAmount(newOrder.getOrderAmount());
        Order updatedOrder = orderRepository.save(existingOrder);
        return existingOrder;
    }
    public String deleteOrder(Long id){
        Order order = getOrderById(id);
        orderRepository.delete(order);
        return "Order Deleted Successfully..."+id;
    }

}
