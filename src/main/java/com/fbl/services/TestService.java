package com.fbl.services;

import com.fbl.configuration.Swagger2WordProp;

public class TestService {

    private Swagger2WordProp swagger2WordProp;

    public TestService(Swagger2WordProp swagger2WordProp){
        this.swagger2WordProp = swagger2WordProp;
    }

    public String getUrl () {
        return swagger2WordProp.getUrl();
    }

}
