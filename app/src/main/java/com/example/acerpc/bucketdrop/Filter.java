package com.example.acerpc.bucketdrop;

/**
 * Created by AcerPC on 11/25/2016.
 */

public interface Filter {
    int NONE = 0;
    int MOST_TIME_LEFT = 1;
    int LEAST_TIME_LEFT = 2;
    int COMPLETE = 3;
    int INCOMPLETE = 4;
}
