package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.StudentInf;

public class StudentINFOutputDTO extends OutPutDTO<StudentInf> {
    public StudentINFOutputDTO(StudentInf realObj) {
        super(realObj);
    }

    public String studentId;
    public StudentInf.Degree degree;
}
