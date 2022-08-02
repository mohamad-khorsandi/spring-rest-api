package ir.sobhan.service.user.model.output;

import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;

public class StudentINFOutputDTO extends OutPutDTO<StudentInf> {
    public StudentINFOutputDTO(StudentInf realObj) throws Exception {
        super(realObj);
    }
    public String studentId;
    public StudentInf.Degree degree;
}
