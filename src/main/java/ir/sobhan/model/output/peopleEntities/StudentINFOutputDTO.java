package ir.sobhan.model.output.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.StudentInf;
import ir.sobhan.model.output.OutPutDTO;

public class StudentINFOutputDTO extends OutPutDTO<StudentInf> {
    public StudentINFOutputDTO(StudentInf realObj) throws Exception {
        super(realObj);
    }
    public String studentId;
    public StudentInf.Degree degree;
}
