package ir.sobhan.service.AbstractService.model.output;

import ir.sobhan.business.exception.CanNotConvertDTOException;
import org.springframework.hateoas.EntityModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

abstract public class OutPutDTO<R_CLASS> {
    public OutPutDTO(R_CLASS realObj) {
        Arrays.stream(this.getClass().getFields()).forEach(outFiled -> setField(realObj, outFiled));
    }

    public OutPutDTO() {
    }

    void setField(R_CLASS realObj, Field outField) {
        try {
            Field realField = realObj.getClass().getDeclaredField(outField.getName());
            realField.setAccessible(true);

            Object realValue = realField.get(realObj);

            if (realValue == null) return;

            if (OutPutDTO.class.isAssignableFrom(outField.getType())) {
                Constructor constructor = outField.getType().getConstructor(realField.getType());
                OutPutDTO outValueDTO = (OutPutDTO) constructor.newInstance(realValue);
                outField.set(this, outValueDTO);
                return;
            }
            outField.set(this, realValue);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 InstantiationException e) {
            throw new CanNotConvertDTOException(e);
        }
    }

    public EntityModel<? extends OutPutDTO> toModel() {
        return EntityModel.of(this.getClass().cast(this));
    }
}
