package org.example.studentLessonServlet.service;

import org.example.studentLessonServlet.db.DBConnectionProvider;
import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentService {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private LessonService lessonService = new LessonService();
    private UserService userService = new UserService();

    public void add(Student student) {

        String checkSql = String.format(
                "SELECT COUNT(*) FROM student WHERE email = '%s';",
                student.getEmail()
        );
        try {
            Statement checkStatement = connection.createStatement();
            ResultSet resultSet = checkStatement.executeQuery(checkSql);
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                throw new IllegalArgumentException("A student with this email already exists: " + student.getEmail());
            }
            int age = student.getAge();
            String sql = String.format(
                    "INSERT INTO student(name, surname, email, age, lesson_id, user_id) VALUES ('%s', '%s', '%s', '%d', '%d',  '%d');",
                    student.getName(),
                    student.getSurname(),
                    student.getEmail(),
                    age,
                    student.getLesson().getId(),
                    student.getUser().getId()
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int anInt = generatedKeys.getInt(1);
                student.setId(anInt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * from student";
        List<Student> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setEmail(resultSet.getString("email"));
                student.setAge(resultSet.getInt("age"));
                student.setLesson(lessonService.getLessonById(resultSet.getInt("lesson_id")));
                student.setUser(userService.getUserById(resultSet.getInt("user_id")));
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int calculateAge(int birthYear) {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - birthYear;
    }

    public List<Student> searchByStudentSurname(String keyword) {
        String sql = "SELECT * from student WHERE surname LIKE '%" + keyword + "%'";
        List<Student> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setEmail(resultSet.getString("email"));
                student.setAge(resultSet.getInt("age"));
                student.setLesson(lessonService.getLessonById(resultSet.getInt("lesson_id")));
                student.setUser(userService.getUserById(resultSet.getInt("user_id")));
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * from student WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setEmail(resultSet.getString("email"));
                student.setAge(resultSet.getInt("age"));
                student.setLesson(lessonService.getLessonById(resultSet.getInt("lesson_id")));
                student.setUser(userService.getUserById(resultSet.getInt("user_id")));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = " + id;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateStudent(Student student) {
        int birthYear = student.getAge();
        String sql = "UPDATE student SET name = ?, surname = ?, email = ?, age = ?, lesson_id = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getEmail());
            statement.setInt(4, birthYear);
            statement.setInt(5, student.getLesson().getId());
            statement.setInt(6, student.getUser().getId());
            statement.setInt(7, student.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student info updated successfully!");
            } else {
                System.out.println("Student with that id is not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudentByUser(int userId) {
        String query = "SELECT * FROM student WHERE user_id = " + userId;
        List<Student> students = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Student student = new Student();
                student.setId(result.getInt("id"));
                student.setName(result.getString("name"));
                student.setSurname(result.getString("surname"));
                student.setEmail(result.getString("Email"));
                student.setAge(result.getInt("age"));
                student.setLesson(lessonService.getLessonById(result.getInt("lesson_id")));
                student.setUser(userService.getUserById(result.getInt("user_id")));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addStudentForUser(Student student, int userId) {

        String checkSql = String.format(
                "SELECT COUNT(*) FROM student WHERE email = '%s' AND user_id = %d",
                student.getEmail(), userId
        );
        String insertSql = String.format(
                "INSERT INTO student(name, surname, email, age, lesson_id, user_id) VALUES ('%s', '%s', '%s', '%d', '%d', '%d')",
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getAge(),
                student.getLesson().getId(),
                userId
        );
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkSql);
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Student with that email already exists for this user.");
                return;
            }
            statement.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                student.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emailCheck(String email) {
        String sql = "SELECT COUNT(*) FROM student WHERE email = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
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



