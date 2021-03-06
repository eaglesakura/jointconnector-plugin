package com.eaglesakura.jc.parser.importer
/**
 * Annotationに含まれる引数を管理する
 */
public class AnnotationArgment {
    Object annotation;
    Map<String, String> argments = new HashMap<String, String>();

    public AnnotationArgment(Object annotation) {
        this.annotation = annotation;

        try {
            String annotationStr = annotation.toString();
            annotationStr = annotationStr.substring(annotationStr.indexOf("(") + 1, annotationStr.length() - 1);
            String[] keyAndValues = annotationStr.split(", ");

            for (String kv : keyAndValues) {
                String key = kv.substring(0, kv.indexOf('='));
                String value = kv.substring(key.length() + 1);
                if (value.startsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                argments.put(key, value);
            }
        } catch (Exception e) {

        }
    }

    /**
     * Annotationの引数を取得する
     * @param key
     * @param defValue
     * @return
     */
    public String getArgment(String key, String defValue) {
        String result = argments.get(key);
        if (result == null || result.isEmpty()) {
            return defValue;
        }

        return result;
    }

    /**
     * Annotationの引数をboolenで取得する
     * @param key
     * @param defValue
     * @return
     */
    public boolean getArgment(String key, boolean defValue) {
        String value = getArgment(key, Boolean.toString(defValue));
        return Boolean.valueOf(value);
    }

    /**
     * 特定のkeyとvalueが一致したらtrueを返す
     * @param key
     * @param value
     * @return
     */
    public boolean isArgmentEquals(String key, String value) {
        String argValue = getArgment(key, "");
        return argValue.equals(value);
    }

    /**
     * 引数からEnumを取得する
     * @param < T >
     * @param key
     * @param def
     * @return
     */
    public <T extends Enum<?>> T getEnum(String key, T defValue) {
        String param = getArgment(key, defValue.name());
        if (param.indexOf('.') > 0) {
            param = param.substring(param.lastIndexOf('.') + 1);
        }

        return (T) Enum.valueOf(defValue.getClass(), param);
    }
}