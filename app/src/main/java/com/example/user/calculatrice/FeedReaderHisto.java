package com.example.user.calculatrice;

import android.provider.BaseColumns;

final class FeedReaderHisto {

    private FeedReaderHisto() {}


    static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "Calculs";
        static final String COLUMN_NAME_TITLE = "expression";
        static final String COLUMN_NAME_SUBTITLE = "result";

    }
}
