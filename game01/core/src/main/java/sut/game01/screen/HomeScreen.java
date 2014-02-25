package sut.game01.screen;

import playn.core.*;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import playn.core.Font;
import tripleplay.ui.*;
import tripleplay.ui.Button;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

import java.awt.*;


/**
 * Created by nose on 2/11/14.
 */
public class HomeScreen extends UIScreen{
    public static final Font TITLE_FONT = PlayN.graphics().createFont(
            "Helvetica",
            Font.Style.BOLD,24);

    private final ScreenStack ss;

    private Root root;


    @Override
    public void wasShown() {
        super.wasShown();

        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(),layer);
        root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10))
        );
        root.setSize(width(),height());

        root.add(new Label("Welcome to MyGame").addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));

        root.add(new Button("Start").onClick(new UnitSlot() {
            @Override
            public void onEmit() {
                ss.push(new TestScreen(ss));
            }
        }));
        

    }

    public HomeScreen(ScreenStack ss) {
        this.ss = ss;


    }
}
