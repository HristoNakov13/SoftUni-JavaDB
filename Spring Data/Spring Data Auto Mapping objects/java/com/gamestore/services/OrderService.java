package com.gamestore.services;

import com.gamestore.domain.entities.Order;
import com.gamestore.domain.models.OrderCartModel;
import com.gamestore.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private ModelMapper modelMapper;
    private OrderRepository orderRepository;

    public OrderService(ModelMapper modelMapper, OrderRepository orderRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
    }

    public void buyGames(OrderCartModel orderCartModel) {
        Order order = this.modelMapper.map(orderCartModel, Order.class);

        this.orderRepository.save(order);
    }
}
