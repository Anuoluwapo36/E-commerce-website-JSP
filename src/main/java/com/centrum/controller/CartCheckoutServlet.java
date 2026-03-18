package com.centrum.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import com.centrum.bean.Cart;
import com.centrum.bean.Order;
import com.centrum.bean.Order_items;
import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.dao.AddressDao;
import com.centrum.dao.CartDao;
import com.centrum.dao.OrderDao;
import com.centrum.dao.OrderItemsDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart/checkout")
public class CartCheckoutServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private OrderDao orderDao = new OrderDao();
    private OrderItemsDao orderItemsDao = new OrderItemsDao();
    private CartDao cartDao = new CartDao();
    private AddressDao addressDao = new AddressDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login-user.jsp");
            return;
        }

        try {
            List<Cart> cartItems = cartDao.getActiveCartItemsByUser(user.getUser_id());
            if (cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart?error=Cart is empty");
                return;
            }

            User_address address = addressDao.getAddress(user.getUser_id());
            if (address == null) {
                response.sendRedirect(request.getContextPath() + "/cart?error=Please add a default address first");
                return;
            }

            double total = 0;
            for (Cart c : cartItems) total += c.getPrice() * c.getQuantity();

            Order order = new Order();
            order.setUser_id(user.getUser_id());
            order.setTotal_amount(total);
            order.setStatus("pending");
            order.setPayment_status("pending");
            order.setPayment_method("not selected");
            order.setAddress_id(address.getAddress_id());
            order.setCreate_at(new java.sql.Timestamp(System.currentTimeMillis()));

            int orderId = orderDao.createOrder(order);

            for (Cart c : cartItems) {
                Order_items item = new Order_items();
                item.setOrder_id(orderId);
                item.setProduct_id(c.getProduct_id());
                item.setQuantity(c.getQuantity());
                item.setPrice(c.getPrice());
                orderItemsDao.createOrderItems(item);
            }


            response.sendRedirect(request.getContextPath() + "/payment-method?order_id=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=Checkout failed");
        }
    }
}
