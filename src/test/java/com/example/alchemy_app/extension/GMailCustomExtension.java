package com.example.alchemy_app.extension;


import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;

public class GMailCustomExtension extends GreenMailExtension {

    public GMailCustomExtension() {
        super(ServerSetupTest.SMTP);
    }
    public GreenMailExtension withConfiguration() {
        return super.withConfiguration(GreenMailConfiguration.aConfig().withUser("test","test")).withPerMethodLifecycle(false);
    }

}
