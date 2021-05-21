package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Override
    public List<News> allNews() {
        return null;
    }

    @Override
    public News findNews(Integer id) {
        return null;
    }
}
