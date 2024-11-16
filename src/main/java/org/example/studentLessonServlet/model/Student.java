package org.example.studentLessonServlet.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    private int id;
    private String name;
    private String surname;
    private String email;
    private int age;
    private Lesson lesson;

    public Student(String name, String surname, String email, int age, Lesson lesson) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.lesson = lesson;
    }
}
