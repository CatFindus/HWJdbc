package controller;

import dao.ChatDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chat;
import view.JsonView;
import view.View;
import mapper.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "chats", urlPatterns = "/chats/*")
public class ChatController extends HttpServlet {

    private ChatDao dao = new ChatDao();
    private JsonMapper mapper = new JsonMapper();
    private View view;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        view = new JsonView(resp);
        try {
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
            Chat chat = dao.getById(Integer.parseInt(path));
            view.update(List.of(chat));
    }

    private void getAll(HttpServletResponse resp) {
            List<Chat> chats = dao.getAll();
            view.update(new ArrayList<>(chats));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            view = new JsonView(resp);
            Chat chat = mapper.getDtoFromRequest(Chat.class, req.getReader());
            chat = dao.create(chat);
            view.update(List.of(chat));
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
            Chat chat = mapper.getDtoFromRequest(Chat.class, req.getReader());
            chat = dao.update(chat, id);
            view.update(List.of(chat));
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
