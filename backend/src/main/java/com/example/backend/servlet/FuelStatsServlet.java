package com.example.backend.servlet;

import com.example.backend.dto.ErrorResponse;
import com.example.backend.dto.FuelStatsResponse;
import com.example.backend.exception.CarNotFoundException;
import com.example.backend.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Manual HttpServlet implementation for fuel stats.
 * Demonstrates manual query parameter parsing, status code setting, and JSON writing.
 */
@WebServlet("/servlet/fuel-stats")
public class FuelStatsServlet extends HttpServlet {
    
    @Autowired
    private CarService carService;
    
    private ObjectMapper objectMapper;
    
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String carIdParam = request.getParameter("carId");
        
        if (carIdParam == null || carIdParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            
            ErrorResponse error = new ErrorResponse(
                HttpServletResponse.SC_BAD_REQUEST,
                "carId parameter is required",
                request.getRequestURI()
            );
            
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(error));
            out.flush();
            return;
        }
        
        Long carId;
        try {
            carId = Long.parseLong(carIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            
            ErrorResponse error = new ErrorResponse(
                HttpServletResponse.SC_BAD_REQUEST,
                "carId must be a valid number",
                request.getRequestURI()
            );
            
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(error));
            out.flush();
            return;
        }
        
        try {
            FuelStatsResponse stats = carService.getFuelStats(carId);
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            
            String jsonResponse = objectMapper.writeValueAsString(stats);
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();
            
        } catch (CarNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            
            ErrorResponse error = new ErrorResponse(
                HttpServletResponse.SC_NOT_FOUND,
                e.getMessage(),
                request.getRequestURI()
            );
            
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(error));
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            
            System.err.println("Unexpected error in servlet: " + e.getMessage());
            e.printStackTrace();
            
            ErrorResponse error = new ErrorResponse(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
            );
            
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(error));
            out.flush();
        }
    }
}
