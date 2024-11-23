package org.example.studentLessonServlet.service;

import org.example.studentLessonServlet.db.DBConnectionProvider;
import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LessonService {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private UserService userService = new UserService();

    public void add(Lesson lesson) {
        String sql = String.format(
                "INSERT INTO lesson(name, duration, lecturer_name, price, user_id) VALUES ('%s', '%s', '%s', '%s', '%s')",
                lesson.getName(),
                lesson.getDuration(),
                lesson.getLecturerName(),
                lesson.getPrice(),
                lesson.getUser().getId());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                lesson.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Lesson> getAllLessons() {
        List<Lesson> result = new ArrayList<>();
        String sql = "SELECT * FROM lesson";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setName(resultSet.getString("name"));
                lesson.setDuration(resultSet.getInt("duration"));
                lesson.setLecturerName(resultSet.getString("lecturer_name"));
                lesson.setPrice(resultSet.getDouble(("price")));
                lesson.setUser(userService.getUserById(resultSet.getInt("user_id")));
                result.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Lesson getLessonById(int id) {
        String sql = "SELECT * FROM lesson WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(resultSet.getInt("id"));
                    lesson.setName(resultSet.getString("name"));
                    lesson.setDuration(resultSet.getInt("duration"));
                    lesson.setPrice(resultSet.getDouble("price"));
                    lesson.setUser(userService.getUserById(resultSet.getInt("user_id")));
                    return lesson;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Mistake from method of LessonService " + sql, e);
        }
        return null;
    }

    public void deleteLesson(int lessonId) {
        String sql = "DELETE FROM lesson WHERE id = " + lessonId;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLesson(Lesson lesson) {
        String sql = "UPDATE lesson SET name = ?, duration = ?, lecturer_name = ?, price = ?, user_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setInt(2, lesson.getDuration());
            preparedStatement.setString(3, lesson.getLecturerName());
            preparedStatement.setDouble(4, lesson.getPrice());
            preparedStatement.setInt(5, lesson.getUser().getId());
            preparedStatement.setInt(6, lesson.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getLessonsByUser(User user) {
        String sql = "SELECT * FROM lesson WHERE user_id = ?";
        List<Lesson> lessons = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(result.getInt("id"));
                lesson.setName(result.getString("name"));
                lesson.setDuration(result.getInt("duration"));
                lesson.setLecturerName(result.getString("lecturer_name"));
                lesson.setPrice(result.getDouble("price"));
                lesson.setUser(user);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public void addLessonForUser(Lesson lesson, int userId) {
        String checkSql = "SELECT COUNT(*) FROM lesson WHERE name = ? AND user_id = ?";
        String insertSql = "INSERT INTO lesson(name, duration, lecturer_name, price, user_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)
        ) {
            checkStatement.setString(1, lesson.getName());
            checkStatement.setInt(2, userId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    System.out.println("Lesson with that name already exists");
                    return;
                }
            }
            insertStatement.setString(1, lesson.getName());
            insertStatement.setInt(2, lesson.getDuration());
            insertStatement.setString(3, lesson.getLecturerName());
            insertStatement.setDouble(4, lesson.getPrice());
            insertStatement.setInt(5, userId);

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        lesson.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                System.out.println("Failed to insert lesson");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding lesson to database", e);
        }
    }

    public boolean sameLessonCheck(String lesson, int userId) {
        String sql = "SELECT COUNT(*) FROM lesson WHERE LOWER(name) = LOWER(?) AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, lesson);
            statement.setInt(2, userId);


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sameLessonCheckEdit(String lesson, int userId, int lessonId) {

        String sql = "SELECT COUNT(*) FROM lesson WHERE LOWER(name) = LOWER(?) AND user_id = ? AND id != ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lesson);
            statement.setInt(2, userId);
            statement.setInt(3, lessonId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
