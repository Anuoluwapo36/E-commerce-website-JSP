package com.centrum.util;

import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.dao.AddressDao;

/**
 * This AddressService exists because I have two separate
 * servlets—UserAddressServlet and CreateOrderServlet both needing to fetch a
 * user's address. Here's the problem I faced before: Initially, I tried to
 * handle address fetching directly in each servlet. But when one servlet passed
 * data to the other (like CreateOrderServlet needing address data for the
 * payment page), the address would often come up null. Why? Because servlets
 * are separate components when you redirect or forward between them, request
 * attributes can get lost, or I’d end up duplicating the same address fetch
 * code in both servlets (which meant if I fixed a bug in one, I’d forget to fix
 * it in the other). For example: UserAddressServlet is for managing addresses,
 * and CreateOrderServlet is for processing orders and showing the payment page.
 * Both need to get the logged-in user’s address from the database, but trying
 * to call one servlet’s method from the other caused red lines (since servlets
 * are managed by the server) or null data (since the request context wasn’t
 * preserved). So I created this service to solve two big issues: No more
 * duplicate code: Both servlets now call this one static method instead of
 * having their own address-fetch logic. No more null data: This service
 * directly uses AddressDao to fetch the address using the user’s ID (from the
 * logged-in user), so the data is reliably retrieved every time—no more lost
 * data between servlets. The goal is simple: Make sure UserAddressServlet and
 * CreateOrderServlet both get the correct user address without hassle, and keep
 * the code clean and easy to update later.
 */

public class AddressService {

	public static User_address getDefaultAddress(User loggedInUser) throws SQLException

	{
		AddressDao addressDao = new AddressDao();
		return addressDao.getAddress(loggedInUser.getUser_id());

	}
	
	public static void createAddress(User_address  address)throws SQLException{
		
		AddressDao create = new AddressDao();
		create.createAddress(address);
		
	}         
	
	public static void deleteUserOldAddress(User user )throws SQLException{
		AddressDao deleteAction = new AddressDao();
		deleteAction.deleteAddressByUserId(user);
		
	}
	
    public static void updateDefaultAddress(User user, User_address newAddress) throws SQLException {
        AddressDao addressDao = new AddressDao();
        addressDao.updateDefaultAddress(user, newAddress);
    }	

	
}
