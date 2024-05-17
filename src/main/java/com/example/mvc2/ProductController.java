package com.example.mvc2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductController", value = "/ProductController")
public class ProductController extends HttpServlet {
    ArrayList<Product> products = new ArrayList<Product>();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);
            // add new product
            String action = req.getParameter("action");
            switch (action) {
                case "add":
                    addProduct(req, resp);
                    break;

                case "delete":
                    deleteProduct(req, resp);
                    break;
            }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String action = req.getParameter("action");
        if(action == null) {
            action = "list"; //default return list of product
        }
        switch (action) {
            case "list":
                listProduct(req,resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
        }
    }

    //biz coding here
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Step 1 call controller
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        // Step 2 instance of model
        Product product = new Product(products.size()+1, name, price);

        products.add(product);
//        response.sendRedirect("product-detail.jsp");
        response.sendRedirect("ProductController?action=list");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i); //delete in database
                break;
            }
        }

        // Giao tiep voi cac Servelet khac va voi view(jsp)
        response.sendRedirect("ProductList?action=list"); //Servlet Communication

    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiet lap thuoc tinh tra ve cho view tu controller (step 3)
        request.setAttribute("products", products);

        // Giao tiep voi cac Servelet khac va voi view(jsp)
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }
}
