package org.example.studentLessonServlet.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    private int id;
    private String name;
    private int duration;
    private String lecturerName;
    private double price;


    public Lesson(String name, int duration, String lecturerName, double price) {
        this.name = name;
        this.duration = duration;
        this.lecturerName = lecturerName;
        this.price = price;
    }
}
