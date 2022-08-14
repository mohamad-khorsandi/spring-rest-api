package ir.sobhan.service.AbstractService.model.input;

import java.lang.reflect.Field;
import java.util.Arrays;

abstract public class InputDTO<R_CLASS> {
    public InputDTO(Class<R_CLASS> type) {
        this.realClassType = type;
    }

    private final Class<R_CLASS> realClassType;

    /**
     * @param realObj null -> to make new instance and init with dto
     *                obj ->  to change given instance fields with not null fields of dto
     */
    public R_CLASS toRealObj(R_CLASS realObj) throws Exception {
        if (realObj == null)
            realObj = realClassType.getConstructor().newInstance();

        R_CLASS finalRealObj = realObj;

        Arrays.stream(this.getClass().getFields()).forEach(inputField -> setField(finalRealObj, inputField));

        return realObj;
    }

    void setField(R_CLASS realObj, Field inputField) {
        try {
            Object inputValue = inputField.get(this);

            if (inputValue == null) return;

            Field realField = realObj.getClass().getDeclaredField(inputField.getName());
            realField.setAccessible(true);

            if (inputValue instanceof InputDTO) {
                InputDTO DTOInputValue = (InputDTO) inputValue;
                Object realInputValue = DTOInputValue.toRealObj(realField.get(realObj));
                realField.set(realObj, realInputValue);
                return;
            }
            realField.set(realObj, inputValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
