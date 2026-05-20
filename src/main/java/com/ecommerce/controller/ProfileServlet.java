package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig(maxFileSize = 1024 * 1024 * 3)
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("authUser");
        Part photo = request.getPart("profilePhoto");
        if (photo == null || photo.getSize() == 0) {
            request.getSession().setAttribute("error", "Please choose a profile photo.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        String submittedName = photo.getSubmittedFileName();
        String extension = extensionOf(submittedName);
        if (!isAllowedImage(extension)) {
            request.getSession().setAttribute("error", "Please upload JPG, JPEG, PNG, or GIF image only.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        String folderPath = getServletContext().getRealPath("/images/profiles");
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new ServletException("Could not create profile folder");
        }

        String fileName = "user-" + user.getId() + "-" + System.currentTimeMillis() + extension;
        String relativePath = "images/profiles/" + fileName;
        photo.write(new File(folder, fileName).getAbsolutePath());

        try {
            userDAO.updateProfilePhoto(user.getId(), relativePath);
            user.setProfilePhoto(relativePath);
            request.getSession().setAttribute("authUser", user);
            request.getSession().setAttribute("success", "Profile photo updated successfully.");
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (SQLException e) {
            throw new ServletException("Could not update profile photo", e);
        }
    }

    private String extensionOf(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
    }

    private boolean isAllowedImage(String extension) {
        return ".jpg".equals(extension) || ".jpeg".equals(extension) || ".png".equals(extension) || ".gif".equals(extension);
    }
}
