package com.example.application.views.timer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;

@Tag("timer-component")
@JavaScript("./js/timer.js")
public class TimerComponent extends Component {

    public void start() {
        getElement().callJsFunction("startTimer");
    }
    
    public void pause() {
        getElement().callJsFunction("pauseTimer");
    }
    
    public void reset() {
        getElement().callJsFunction("resetTimer");
    }
    
    public void stop() {
        getElement().callJsFunction("stopTimer");
    }
}
