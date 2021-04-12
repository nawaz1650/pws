package com.shahnawaz.pws.repos;

import com.shahnawaz.pws.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Query("select u.user_id,u.mobile,O.order_id,O.qty,O.address,O.order_date,O.created_date from Order O JOIN O.user u")
    public List<Object[]> findAllOrders();
}
