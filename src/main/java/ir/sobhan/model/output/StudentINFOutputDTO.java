package ir.sobhan.model.output;

import ir.sobhan.model.entity.StudentInf;

public class StudentINFOutputDTO extends OutPutDTO<StudentInf> {
    public StudentINFOutputDTO(StudentInf realObj) throws Exception {
        super(realObj);
    }
    public String studentId;
    public StudentInf.Degree degree;
}
