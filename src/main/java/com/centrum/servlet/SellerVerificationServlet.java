package com.centrum.servlet;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;




@WebServlet("/verify/identity")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, 
		maxFileSize = 10 * 1024 * 1024, 
		maxRequestSize = 20 * 1024 * 1024 
)
public class SellerVerificationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SellerDAO sellerDAO = new SellerDAO();
	private static final String UPLOAD_DIR = "uploads/verification";



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register");
			return;

		}

		request.setAttribute("seller", session.getAttribute("seller"));
		request.getRequestDispatcher("/WEB-INF/seller/view/verification.jsp").forward(request, response);

	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register");
			return;

		}

		request.setCharacterEncoding("UTF-8");
		int sellerId = (int) session.getAttribute("seller");

		try {


			Part profilePhotoPart = request.getPart("profilePhoto");
			Part businessLicensePart = request.getPart("businessLicense");

			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdirs();

			String idFilePath = null;
			String licenseFilePath = null;
			boolean updated = false;

			if (profilePhotoPart != null && profilePhotoPart.getSize() > 0) {


				String profileFileName = "seller_" + sellerId + "_id" + getFileExtension(profilePhotoPart);
				Files.copy(profilePhotoPart.getInputStream(), new File(uploadDir, profileFileName).toPath(),
						StandardCopyOption.REPLACE_EXISTING);

				idFilePath = UPLOAD_DIR + "/" + profileFileName;
				updated = true;

			}

			if (businessLicensePart != null && businessLicensePart.getSize() > 0) {


				String licenseFileName = "seller_" + sellerId + "_license" + getFileExtension(businessLicensePart);
				Files.copy(businessLicensePart.getInputStream(), new File(uploadDir, licenseFileName).toPath(),
						StandardCopyOption.REPLACE_EXISTING);

				licenseFilePath = UPLOAD_DIR + "/" + licenseFileName;
				updated = true;

			}

			if (updated) {


				sellerDAO.submitVerificationDocuments(sellerId, idFilePath, licenseFilePath);

				response.sendRedirect(request.getContextPath() + "/verification/success");

			} else {


				request.setAttribute("error", "No file selected. Please upload a document.");
				request.setAttribute("seller", sellerId);
				request.getRequestDispatcher("/WEB-INF/seller/view/verification.jsp").forward(request, response);

			}

		} catch (Exception e) {


			e.printStackTrace();
			request.setAttribute("error", "An unexpected error occurred. Please try again.");
			request.setAttribute("seller", sellerId);
			request.getRequestDispatcher("/WEB-INF/seller/view/verification.jsp").forward(request, response);

		}

	}



	private String getFileExtension(Part part) {

		String filename = part.getSubmittedFileName();
		int dotIndex = filename.lastIndexOf('.');
		return (dotIndex > 0) ? filename.substring(dotIndex) : "";

	}



}
