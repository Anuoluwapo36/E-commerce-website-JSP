

package com.centrum.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.centrum.connections.MySQLcnn;
import com.centrum.model.Seller;




public class SellerDAO {

	private MySQLcnn db = new MySQLcnn();



	public boolean isEmailExists(String email) {

		boolean exists = false;
		String sql = "SELECT email FROM sellers WHERE email = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {


				exists = true;

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return exists;

	}



	public boolean registerSeller(Seller seller) {

		boolean success = false;
		String sql = "INSERT INTO sellers (email, password, verification_status, is_active) VALUES (?, ?, ?, ?)";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, seller.getEmail());
			ps.setString(2, seller.getPassword());
			ps.setString(3, seller.getVerificationStatus());
			ps.setBoolean(4, seller.getIsActive());

			success = ps.executeUpdate() > 0;

		} catch (Exception e) {


			e.printStackTrace();

		}

		return success;

	}



	public int getSellerIdByEmail(String email) {

		int id = -1;
		String sql = "SELECT seller_id FROM sellers WHERE email = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, email);
			var rs = ps.executeQuery();

			if (rs.next()) {


				id = rs.getInt("seller_id");

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return id;

	}



	/**
	 * Updates shop details for a given seller.
	 *
	 * @param sellerId  the ID of the seller
	 * @param shopName  the name of the shop
	 * @param shopType  the type of the shop
	 * @param ownerName the name of the shop owner
	 * @param phone     the contact phone number
	 * @return true if the update was successful, false otherwise
	 */

	public boolean updateShopDetails(int sellerId, String shopName, String shop_type,  String ownerName, String phone) {

		boolean success = false;
		String sql = "UPDATE sellers SET shop_name = ?, shop_type =? , owner_name = ?, phone = ? WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, shopName);
			ps.setString(2, shop_type);
			ps.setString(3, ownerName);
			ps.setString(4, phone);
			ps.setInt(5, sellerId);

			success = ps.executeUpdate() > 0;

		} catch (Exception e) {


			e.printStackTrace();

		}

		return success;

	}

	public boolean updateProfileDeatils(int sellerId, String shopName,   String ownerName, String phone) {

		boolean success = false;
		String sql = "UPDATE sellers SET shop_name = ?, owner_name = ?, phone = ? WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, shopName);
			ps.setString(2, ownerName);
			ps.setString(4, phone);
			ps.setInt(5, sellerId);

			success = ps.executeUpdate() > 0;

		} catch (Exception e) {


			e.printStackTrace();

		}

		return success;

	}


	/**
	 * Checks if a seller exists with the given seller_id
	 *
	 * @param sellerId the ID of the seller
	 * @return true if the seller exists, false otherwise
	 */
	public boolean isSellerIdExists(int sellerId) {

		boolean exists = false;
		String sql = "SELECT seller_id FROM sellers WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			var rs = ps.executeQuery();

			if (rs.next()) {


				exists = true;

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return exists;

	}



	/**
	 * Submits verification documents for a seller.
	 *
	 * @param sellerId            the ID of the seller
	 * @param idImagePath         the file path or URL of the government ID image
	 * @param businessLicensePath the file path or URL of the business license image
	 * @return true if the verification documents were successfully stored, false
	 *         otherwise
	 */
	public boolean submitVerificationDocuments(int sellerId, String idDocumentPath, String businessLicensePath) {

		boolean success = false;
		String sql = "UPDATE sellers SET id_document = ?, business_license = ?, verification_status = 'pending' WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, idDocumentPath);
			ps.setString(2, businessLicensePath);
			ps.setInt(3, sellerId);

			success = ps.executeUpdate() > 0;

		} catch (Exception e) {


			e.printStackTrace();

		}

		return success;

	}



	/**
	 * Checks if a seller has already submitted verification documents.
	 *
	 * @param sellerId the ID of the seller
	 * @return true if documents exist, false otherwise
	 */
	public boolean hasSubmittedVerification(int sellerId) {

		boolean exists = false;
		String sql = "SELECT 'id_document', business_license FROM sellers WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {


				String idImage = rs.getString("id_document");
				String license = rs.getString("business_license");
				exists = (idImage != null && !idImage.isEmpty()) && (license != null && !license.isEmpty());

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return exists;

	}



	public boolean isProfilePhotoUploaded(int sellerId) {

		boolean uploaded = false;
		String sql = "SELECT id_document FROM sellers WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			var rs = ps.executeQuery();

			if (rs.next()) {


				String idDoc = rs.getString("id_document");
				uploaded = idDoc != null && !idDoc.isEmpty();

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return uploaded;

	}



	public boolean isLicenseUploaded(int sellerId) {

		boolean uploaded = false;
		String sql = "SELECT business_license FROM sellers WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			var rs = ps.executeQuery();

			if (rs.next()) {


				String license = rs.getString("business_license");
				uploaded = license != null && !license.isEmpty();

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return uploaded;

	}



	/**
	 * Retrieves the shop name for a given seller.
	 *
	 * @param sellerId the seller ID
	 * @return shop name if found, otherwise null
	 */
	public String getShopNameBySellerId(int sellerId) {

		String sql = "SELECT shop_name FROM sellers WHERE seller_id = ?";

		try (Connection con = db.getCnn(); PreparedStatement ps = con.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {


				return rs.getString("shop_name");

			}

		} catch (SQLException e) {


			e.printStackTrace();

		}

		return null;

	}



	/**
	 * Checks seller credentials for login
	 *
	 * @param email    seller email
	 * @param password seller password
	 * @return Seller object if credentials are valid, otherwise null
	 */
	public Seller loginSeller(String email, String password) {

		Seller seller = null;
		String sql = "SELECT * FROM sellers WHERE email = ? AND is_active = 1";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, email.trim());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {


				String storedHash = rs.getString("password");

				if (org.mindrot.jbcrypt.BCrypt.checkpw(password, storedHash)) {


					seller = new Seller();
					seller.setSellerId(rs.getInt("seller_id"));
					seller.setShopName(rs.getString("shop_name"));
					seller.setShopSlug(rs.getString("shop_slug"));
					seller.setOwnerName(rs.getString("owner_name"));
					seller.setEmail(rs.getString("email"));
					seller.setPassword(storedHash); 
					seller.setPhone(rs.getString("phone"));
					seller.setVerificationStatus(rs.getString("verification_status"));
					seller.setIsActive(rs.getInt("is_active") == 1);
					seller.setBankName(rs.getString("bank_name"));
					seller.setBankAccountNumber(rs.getString("bank_account_number"));
					seller.setCreatedAt(rs.getTimestamp("created_at"));
					seller.setUpdatedAt(rs.getTimestamp("updated_at"));
					seller.setShopType(rs.getString("shop_type"));
					seller.setIdDocument(rs.getString("id_document"));
					seller.setBusinessLicense(rs.getString("business_license"));

				}

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return seller;

	}


	public Seller loginSellerById(int sellerId) {
	    String sql = "SELECT * FROM sellers WHERE seller_id = ?";
	    try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, sellerId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            Seller seller = new Seller();
	            seller.setShopName(rs.getString("shop_name"));
	            seller.setEmail(rs.getString("email"));
	            return seller;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	
	
	
    /**
     * Change the password for a seller.
     * 
     * @param sellerId       Seller ID
     * @param currentPassword Current password input by user
     * @param newPassword    New password
     * @return true if password changed successfully, false otherwise
     */
    public boolean changePassword(int sellerId, String currentPassword, String newPassword) {
        String fetchSql = "SELECT password FROM sellers WHERE seller_id = ?";
        String updateSql = "UPDATE sellers SET password = ? WHERE seller_id = ?";

        try (Connection conn = db.getCnn();
             PreparedStatement fetchPs = conn.prepareStatement(fetchSql)) {

            fetchPs.setInt(1, sellerId);
            ResultSet rs = fetchPs.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                if (!BCrypt.checkpw(currentPassword, storedHash)) {
                    return false;
                    // Current password incorrect
                }

                // Hash the new password
                String hashedNew = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setString(1, hashedNew);
                    updatePs.setInt(2, sellerId);

                    return updatePs.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
