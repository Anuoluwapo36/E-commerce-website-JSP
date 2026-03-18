package com.centrum.util;

import java.sql.SQLException;

import com.centrum.bean.UserProfileImages;
import com.centrum.dao.UserDao;

public class UserProfileServices {
	UserDao userDao = new UserDao();

	/**
	 * Reusable method to fetch a user's default profile image by user ID. Wraps DAO
	 * logic for reusability across servlets/JSP controllers. can be use to get user
	 * profile in any servlet and jsp main reason help you rewriting code.
	 * 
	 * @param userId The unique int ID of the user
	 * @return UserProfileImages bean (with image path) or null if no default image
	 *         exists
	 * @throws SQLException If database error occurs (passed up to caller for
	 *                      handling)
	 */
	public UserProfileImages getDefaultProfile(int userId) throws SQLException {
		return userDao.getDefaultUserImage(userId);

	}

}
