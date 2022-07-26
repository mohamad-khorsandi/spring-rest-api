package ir.sobhan.model.input.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.StudentInf;
import ir.sobhan.model.input.InputDTO;
import lombok.Setter;

@Setter
public class StudentINFInputDTO extends InputDTO<StudentInf> {
    public StudentINFInputDTO() {
        super(StudentInf.class);
    }

    public String studentId;
    public StudentInf.Degree degree;
}
