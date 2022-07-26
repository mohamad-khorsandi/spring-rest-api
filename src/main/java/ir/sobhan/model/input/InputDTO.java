package ir.sobhan.model.input;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
abstract public class InputDTO<R_CLASS>{
    public InputDTO(Class<R_CLASS> type) {
        this.realClassType = type;
    }
    private Class<R_CLASS> realClassType;

    /**
     * @param realObj null -> to make new instance and init with dto
     *                obj ->  to change given instance fields with not null fields of dto
     */
    public R_CLASS toRealObj(R_CLASS realObj) throws Exception {
        if (realObj == null)
            realObj = realClassType.getConstructor().newInstance();

        Field[] inputFields = this.getClass().getFields();

        for (Field inputField : inputFields) {
            Object inputValue = inputField.get(this);

            if (inputValue == null)
                continue;

            Field realField = realObj.getClass().getDeclaredField(inputField.getName());
            realField.setAccessible(true);

            if (inputValue instanceof InputDTO) {
                InputDTO DTOinputValue = (InputDTO) inputValue;
                Object realInputValue = DTOinputValue.toRealObj(realField.get(realObj));
                realField.set(realObj, realInputValue);
                continue;
            }

            realField.set(realObj, inputValue);
        }
        return realObj;
    }
}
