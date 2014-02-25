package sut.game01.screen;

import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

/**
 * Created by nose on 2/11/14.
 */
public class TestScreen extends Screen {

    private final ScreenStack ss;


    public TestScreen(ScreenStack ss) {
        this.ss = ss;
    }

    @Override
    public void wasShown() {
        super.wasShown();
        Image startImage = assets().getImage("images/start.png");
        ImageLayer start = graphics().createImageLayer(startImage);
        layer.add(start);
        start.setTranslation(200,0);

        start.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new GameScreen(ss));
            }
        });

    }

}
