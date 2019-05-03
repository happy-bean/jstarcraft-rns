package com.jstarcraft.recommendation.data.processor;

import com.jstarcraft.recommendation.data.DataModule;

/**
 * 数据匹配器
 * 
 * @author Birdy
 *
 */
public interface DataMatcher {

    public static DataMatcher discreteOf(DataModule<?> accessor, int dimension) {
        int size = accessor.getSize();
        return (paginations, positions) -> {
            if (positions.length != size) {
                throw new IllegalArgumentException();
            }
            for (int index = 0; index < size; index++) {
                int feature = accessor.getQualityFeature(dimension, index);
                paginations[feature + 1]++;
            }
            int cursor = size;
            for (int index = paginations.length - 1; index > 0; index--) {
                cursor -= paginations[index];
                paginations[index] = cursor;
            }
            for (int index = 0; index < size; index++) {
                int feature = accessor.getQualityFeature(dimension, index);
                positions[paginations[feature + 1]++] = index;
            }
        };
    }

    void match(int[] paginations, int[] positions);

}
