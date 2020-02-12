/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.controller;

import edu.kist_bit.hamrotarkaribazzar.entity.Category;
import edu.kist_bit.hamrotarkaribazzar.services.CategoryJpaController;
import edu.kist_bit.hamrotarkaribazzar.utils.FileUploadDTO;
import edu.kist_bit.hamrotarkaribazzar.utils.FileUploadUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "CategoryController", urlPatterns = {"/category","/addcategory"})
public class CategoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("HamroTarikarBazzaremf");

        CategoryJpaController categoryjpacontroller = new CategoryJpaController(emf);
        List<Category> categoryList = categoryjpacontroller.findCategoryEntities();
        Category category = new Category();

        String redirectURL, ref;
        String servletPath = request.getServletPath();

        switch (servletPath) {
            case "/addcategory" :
                Category createCategory = getFormData(request);
                FileUploadDTO fileUploadDTO = FileUploadUtil.fileUpload(request, response, "file");
                String photo = fileUploadDTO.getFileLocation();

                createCategory.setImage(photo);
                categoryjpacontroller.create(createCategory);
                redirectURL = "WEB-INF/admin/addcategory.jsp";
                break;
                
            default :
                redirectURL ="WEB-INF/admindashboard.jsp";
                break;
                 
            
        }
        dispatchRequest(request, response, redirectURL);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void dispatchRequest(HttpServletRequest request, HttpServletResponse response, String redirectURL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Category getFormData(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
