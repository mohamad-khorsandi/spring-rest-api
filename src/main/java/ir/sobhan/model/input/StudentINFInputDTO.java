package ir.sobhan.model.input;

import ir.sobhan.model.entity.StudentInf;
import lombok.Setter;

@Setter
public class StudentINFInputDTO extends InputDTO<StudentInf> {
    public StudentINFInputDTO() {
        super(StudentInf.class);
    }

    public String studentId;
    public StudentInf.Degree degree;
}
