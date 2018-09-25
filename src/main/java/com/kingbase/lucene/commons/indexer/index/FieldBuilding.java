package com.kingbase.lucene.commons.indexer.index;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.util.NumericUtils;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * 字段组合
 *
 * @author ganliang
 */
public class FieldBuilding {

    public Field createField(Map<String, String> fieldAttrMap, String fieldName, Object fieldValue) {
        FieldType FIELDTYPE = new FieldType();
        //字段存储
        String store = fieldAttrMap.get("store");
        String tokenized = fieldAttrMap.get("tokenized");
        String indexed = fieldAttrMap.get("indexed");
        String type = fieldAttrMap.get("type");
        String boost = fieldAttrMap.get("boost");

        if ("TRUE".equalsIgnoreCase(store)) {
            FIELDTYPE.setStored(true);
        }
        //字段分词
        if ("FALSE".equalsIgnoreCase(tokenized)) {
            FIELDTYPE.setTokenized(false);
        }
        //字段索引
        IndexOptions indexOptions = getIndexOptions(indexed);
        FIELDTYPE.setIndexOptions(indexOptions);

        //获取到字段的类型
        Field field = getField(type, FIELDTYPE, fieldName, fieldValue);

        //字段加权
        if (boost != null && !"".equals(boost) && !"".equals(fieldValue.toString())) {
            field.setBoost(Float.parseFloat(boost));
        }
        return field;
    }

    /**
     * 获取域
     *
     * @param type       域类型
     * @param FIELDTYPE  域设置
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return
     */
    private Field getField(String type, FieldType FIELDTYPE, String fieldName, Object fieldValue) {
        fieldName = fieldName.toLowerCase();
        if (type == null) {
            throw new IllegalArgumentException("字段类型不能为空");
        }
        Field field = null;
        switch (type.toUpperCase()) {
            case "INT":
                FIELDTYPE.setOmitNorms(true);
                FIELDTYPE.setNumericType(FieldType.NumericType.INT);
                FIELDTYPE.setNumericPrecisionStep(NumericUtils.PRECISION_STEP_DEFAULT_32);
                FIELDTYPE.freeze();
                field = new IntField(fieldName, Integer.parseInt(fieldValue.toString()), FIELDTYPE);
                break;
            case "FLOAT":
                FIELDTYPE.setOmitNorms(true);
                FIELDTYPE.setNumericType(FieldType.NumericType.DOUBLE);
                FIELDTYPE.freeze();
                field = new FloatField(fieldName, Float.parseFloat(fieldValue.toString()), FIELDTYPE);
                break;
            case "DOUBLE":
                FIELDTYPE.setOmitNorms(true);
                FIELDTYPE.setNumericType(FieldType.NumericType.DOUBLE);
                FIELDTYPE.freeze();
                field = new DoubleField(fieldName, Double.parseDouble(fieldValue.toString()), FIELDTYPE);
                break;
            case "LONG":
                FIELDTYPE.setOmitNorms(true);
                FIELDTYPE.setNumericType(FieldType.NumericType.LONG);
                FIELDTYPE.freeze();
                field = new LongField(fieldName, Long.parseLong(fieldValue.toString()), FIELDTYPE);
                break;
            //string字段
            case "STRING":
                FIELDTYPE.setOmitNorms(true);
                FIELDTYPE.freeze();
                field = new Field(fieldName, fieldValue == null ? "" : fieldValue.toString(), FIELDTYPE);
                break;
            //流的格式
            case "TEXT":
                Reader reader = null;
                if (fieldValue instanceof String) {
                    if (fieldValue != null) {
                        reader = new StringReader(fieldValue.toString());
                    }
                } else if (fieldValue instanceof Reader) {
                    reader = (Reader) fieldValue;
                }
                field = new TextField(fieldName, reader);
                break;
            default:
                break;
        }
        return field;
    }

    /**
     * 获取IndexOptions
     *
     * @param indexed 索引字段
     * @return
     */
    private IndexOptions getIndexOptions(String indexed) {
        //默认的索引
        IndexOptions DEFAULT_INDEXOPTIONS = IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;

        IndexOptions indexOptions = null;
        if (indexed == null) {
            return DEFAULT_INDEXOPTIONS;
        }
        switch (indexed.toUpperCase()) {
            case "NONE":
                indexOptions = IndexOptions.NONE;
                break;
            case "DOCS":
                indexOptions = IndexOptions.DOCS;
                break;
            case "DOCS_AND_FREQS":
                indexOptions = IndexOptions.DOCS_AND_FREQS;
                break;
            case "DOCS_AND_FREQS_AND_POSITIONS":
                indexOptions = IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
                break;
            case "DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS":
                indexOptions = IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
                break;
            default:
                indexOptions = DEFAULT_INDEXOPTIONS;
                break;
        }
        return indexOptions;
    }
}
