package controller;

import dao.MessageDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Message;
import view.JsonView;
import view.View;
import mapper.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "messages", urlPatterns = "/messages/*")
public class MessageController extends HttpServlet {

    private MessageDao dao = new MessageDao();
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
        Message message = dao.getById(Integer.parseInt(path));
        view.update(List.of(message));
    }

    private void getAll(HttpServletResponse resp) {
        List<Message> messages = dao.getAll();
        view.update(new ArrayList<>(messages));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            Message message = mapper.getDtoFromRequest(Message.class, req.getReader());
            message = dao.create(message);
            view.update(List.of(message));
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
            Message message = mapper.getDtoFromRequest(Message.class, req.getReader());
            message = dao.update(message, id);
            view.update(List.of(message));
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
