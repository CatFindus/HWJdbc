package controller;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import view.JsonView;
import view.View;
import mapper.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "users", urlPatterns = "/users/*")
public class UserController extends HttpServlet {

    private UserDao dao;
    private JsonMapper mapper;
    private View view;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            switch (req.getPathInfo()) {
                case "" -> getAll(resp);
                default -> getById(resp, req.getPathInfo());
            }
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(418);
        }
    }

    private void getById(HttpServletResponse resp, String path) {
        User user = dao.getById(Integer.parseInt(path));
        view.update(List.of(user));
    }

    private void getAll(HttpServletResponse resp) {
        List<User> users = dao.getAll();
        view.update(new ArrayList<>(users));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            User user = mapper.getDtoFromRequest(User.class, req.getReader());
            user = dao.create(user);
            view.update(List.of(user));
            resp.setStatus(201);
        } catch (Exception e) {
            resp.setStatus(418);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            int id = Integer.parseInt(req.getPathInfo());
            User user = mapper.getDtoFromRequest(User.class, req.getReader());
            user = dao.update(user, id);
            view.update(List.of(user));
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(418);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            int id = Integer.parseInt(req.getPathInfo());
            dao.delete(id);
            resp.setStatus(204);
        } catch (Exception e) {
            resp.setStatus(418);
        }
    }
}
