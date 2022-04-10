package org.niki.dto;


import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonPropertyOrder({ "id", "familyName", "name", "groups"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    public long id;
    public String name;
    @JsonProperty("surname")
    public String familyName;
    public List<Group> groups;
    public List<Student> friends;


    public Student() {
    }


    public Student(long id, String name, String familyName, List<Group> groups, List<Student> friends) {
        this.id=id;
        this.name = name;
        this.familyName = familyName;
        this.groups = groups;
        this.friends = friends;
    }


    @JsonSetter("friends")
    public void friendSetter(List<Student> friends) {
        this.friends= friends.stream().map(friend -> new Student(0,
                        friend.name,
                        friend.familyName,
                        friend.groups,
                        new ArrayList<>()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return familyName.equals(((Student) o).familyName) && name.equals(((Student) o).name)
                && id==((Student) o).id;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", \n name='" + name + '\'' +
                ", \n familyName='" + familyName + '\'' +
                ", \n groups=" + groups +
                (friends.size()==0? "": " \n friends=" + friends ) +
                ", \n" +
                '}';
    }
}

