import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

interface IList<T> {
  //returns true if any member of the list passes the predicate
  boolean ormap(Predicate<T> pred);
  //combines the items in this list using the given function
  <U> U fold(BiFunction<T, U, U> fun, U base);
}

class MtList<T> implements IList<T> {

  //returns true if any member of the list passes the predicate
  public boolean ormap(Predicate<T> pred) {
    return false;
  }
  
  //combines the items in this list using the given function
  public <U> U fold(BiFunction<T, U, U> fun, U base) {
    return base;
  }
  
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  //returns true if any member of the list passes the predicate
  public boolean ormap(Predicate<T> pred) {
    return pred.test(this.first) || this.rest.ormap(pred);
  }
  
  //combines the items in this list using the given function
  public <U> U fold(BiFunction<T, U, U> fun, U base) {
    return fun.apply(this.first, this.rest.fold(fun, base));
  }
}

class Course {
  String name;
  Instructor prof;
  IList<Student> students;
  
  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MtList<Student>();
  }
  
  // adds the given student to this course
  void addStudent(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }
  
  boolean sameCourse(Course c) {
    return this.name.equals(c.name);
  }
}

class Instructor {
  String name;
  IList<Course> courses;
  
  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }
  
  // returns true if the given student is in more than one of this instructor's courses
  boolean dejavu(Student c) {
    return c.profTwice(this);
  }
  
  // returns true if this prof is the same as the given one
  boolean sameInstructor(Instructor i) {
    return this.name.equals(i.name);
  }
}

class Student {
  String name;
  int ID;
  IList<Course> courses;
  
  Student(String name, int ID) {
    this.name = name;
    this.ID = ID;
    this.courses = new MtList<Course>();
  }
  
  // enrolls a student into the given course
  void enroll(Course c) {
    c.addStudent(this);
    this.courses = new ConsList<Course>(c, this.courses);
  }
  
  // returns true if the given student is classmates with this student
  boolean classmates(Student c) {
    return this.courses.ormap(new CourseInList(c.courses));
  }
  
//  // returns true if the given student is the same as this student
//  boolean sameStudent(Student s) {
//    return this.name.equals(s.name)&& this.ID == s.ID; 
//  }
  
  // returns true if this student has the given professor in more than one course
  boolean profTwice(Instructor i) {
    return this.courses.fold(new TimesInList(i), 0) > 1;
  }
  
}

class CourseInList implements Predicate<Course> {
  IList<Course> courses;
  
  CourseInList(IList<Course> courses) {
    this.courses = courses;
  }
  
  // returns true if the given course is in the list of courses
  public boolean test(Course c) {
    return courses.ormap(new SameCourse(c));
  }
  
}

class SameCourse implements Predicate<Course> {
  Course course;
  
  SameCourse(Course course) {
    this.course = course;
  }
  
  // returns true if the given course is the same as this.course
  public boolean test(Course c) {
    return this.course.sameCourse(c);
  }
  
}

class TimesInList implements BiFunction<Course, Integer, Integer> {
  Instructor instructor;
  
  TimesInList(Instructor instructor) {
    this.instructor = instructor;
  }
  
  public Integer apply(Course c, Integer base) {
    if (this.instructor.sameInstructor(c.prof)) {
      return base + 1;
    } else {
      return base;
    }
  }
  
}

class ExamplesRegistrar {
  Student s1;
  Student s2;
  Student s3;
  Student s4;
  Student s5;
  
  Instructor bob;
  Instructor razzaq;
  Instructor michael;
  
  Course fun1;
  Course fun2;
  Course ood;
  Course algo;
  
  void initData() {
    
    s1 = new Student("S1", 1);
    s2 = new Student("S2", 2);
    s3 = new Student("S3", 3);
    s4 = new Student("S4", 4);
    s5 = new Student("S5", 5);
    
    bob = new Instructor("Bob");
    razzaq = new Instructor("Razzaq");
    michael = new Instructor("Michael");
    
    fun1 = new Course("Fundies 1", this.bob);
    fun2 = new Course("Fundies 2", this.razzaq);
    ood = new Course("Object-Oriented Design", this.razzaq);
    algo = new Course("Algorithms", this.michael);
    
    this.s1.enroll(this.fun1);
    this.s2.enroll(this.fun1);
    this.s3.enroll(this.fun2);
    this.s3.enroll(this.ood);
    this.s4.enroll(this.ood);
    
  }
  
  void testEnroll(Tester t) {
    
  }
  
  void testAddStudent(Tester t) {
    
  }
  
  boolean testClassmates(Tester t) {
    return true;
  }
  
  boolean testCourseInList(Tester t) {
    this.initData();
    return t.checkExpect(this.s1.courses.ormap(new CourseInList(this.s2.courses)), true)
        && t.checkExpect(this.s1.courses.ormap(new CourseInList(this.s3.courses)), false)
        && t.checkExpect(this.s3.courses.ormap(new CourseInList(this.s4.courses)), true)
        && t.checkExpect(this.s4.courses.ormap(new CourseInList(this.s3.courses)), true);
  }
  
//  boolean testSameCourse(Tester t) {
//    return t.checkExpect(this.fun1.sameCourse(fun1), true) 
//        && t.checkExpect(this.ood.sameCourse(fun2), false);
//  }
  
  boolean testOrmap(Tester t) {
    return true;
  }
  
  boolean testFold(Tester t) {
    return true;
  }
  
  boolean testDejavu(Tester t) {
    return true;
  }
  
  boolean testProfTwice(Tester t) {
    return true;
  }
  
  boolean testTimesInList(Tester t) {
    return true;
  }
  
  boolean testSameInstructor(Tester t) {
    return true;
  }
}
