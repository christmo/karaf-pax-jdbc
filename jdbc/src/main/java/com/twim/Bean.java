package com.twim;


import javax.sql.DataSource;

/**
 * Created by christmo on 12/3/16.
 */
public class Bean {

    private DataSource ds;

    public void test() {
        System.out.println("Injected:" + ds);
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }
}
