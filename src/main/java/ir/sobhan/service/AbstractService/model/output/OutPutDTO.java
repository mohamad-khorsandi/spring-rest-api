package ir.sobhan.service.AbstractService.model.output;

import org.springframework.hateoas.EntityModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

abstract public class OutPutDTO<R_CLASS> {
    public OutPutDTO(R_CLASS realObj) {
        try {
            Field[] outFields = this.getClass().getFields();

            for (Field outField : outFields) {
                Field realField = realObj.getClass().getDeclaredField(outField.getName());
                realField.setAccessible(true);

                Object realValue = realField.get(realObj);

                if (realValue == null)
                    continue;

                if (OutPutDTO.class.isAssignableFrom(outField.getType())) {
                    Constructor constructor = outField.getType().getConstructor(realField.getType());
                    OutPutDTO outValueDTO = (OutPutDTO) constructor.newInstance(realValue);
                    outField.set(this, outValueDTO);
                    continue;
                }
                outField.set(this, realValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OutPutDTO() {
    }

    public EntityModel<? extends OutPutDTO> toModel() {
        return EntityModel.of(this.getClass().cast(this));
    }

}
