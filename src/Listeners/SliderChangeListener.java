package Listeners;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderChangeListener implements ChangeListener {

    private final JLabel value;
    private final JSlider slider;

    public SliderChangeListener(JLabel value, JSlider slider) {
        this.value = value;
        this.slider = slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        value.setText(String.valueOf(slider.getValue()));
    }
}
