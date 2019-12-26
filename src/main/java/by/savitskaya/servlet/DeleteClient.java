package by.savitskaya.servlet;

import by.savitskaya.dao.ClientDao;
import by.savitskaya.db.ConnectionToDB;
import by.savitskaya.model.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/delete"})
public class DeleteClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int idClient;
        System.out.println("Enter doGet on delete");
        idClient = Integer.parseInt(request.getParameter("id"));
        System.out.println("idClient = " + idClient);
        if (idClient != 0) {
            Client editClient = ClientDao.getClientById(idClient);
            System.out.println(editClient.toString());
            request.setAttribute("deleteClient", editClient);
            request.getRequestDispatcher("/jsp/deleteClient.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Enter doPost on delete");
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            ClientDao.deleteClientFromDB(id, ConnectionToDB.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listClient", ClientDao.getClientList());
        request.getRequestDispatcher("/jsp/mainPage.jsp").forward(request, response);
    }


}



