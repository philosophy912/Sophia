package com.chinatsp.automotive;

import com.chinatsp.automotive.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutomotiveApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(AutomotiveApplication.class, MainView.class, args);
    }


}
