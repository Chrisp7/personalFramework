package com.imooc.demo.annotation;

@CourseInfoAnnotation(courseName = "java annotation learning",courseTag = "programming language",courseProfile = "help students have an idea of how to customize the annotation")
public class ImoocCourse {

    @PersonInfoAnnotation(name="Pat",languages = {"java","python"})
    private String author;

    @CourseInfoAnnotation(courseName = "college market project",courseTag = "practicing",courseProfile = "guide student how to implement an actual java project.",courseIndex = 144)
    public void getCourseInfo(){

    }
}
