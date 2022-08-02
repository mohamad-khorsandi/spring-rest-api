package ir.sobhan.service.user.model.input;

import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.AbstractService.model.input.InputDTO;
import lombok.Setter;

@Setter
public class StudentINFInputDTO extends InputDTO<StudentInf> {
    public StudentINFInputDTO() {
        super(StudentInf.class);
    }

    public String studentId;
    public StudentInf.Degree degree;
}
